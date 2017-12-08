package com.lq.util.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.lq.model.page.PageInfo;

/**
 * executor 拦截 根据分页参数 命中不同的cache.
 * @author lq
 * 2017-12-6 10:13:43
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class CacheInterceptor implements Interceptor {

	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private static String defaultPageSqlId = ".*Page$"; // 需要拦截的ID(正则匹配)
	private static String pageSqlId = ""; // 需要拦截的ID(正则匹配)
	private static String dialect = "oracle"; // 数据库类型(默认为oracle)

	private Log log = LogFactory.getLog(this.getClass());

	public Object intercept(Invocation invocation) throws Throwable {
		Executor executorProxy = (Executor) invocation.getTarget();
		MetaObject metaExecutor = MetaObject.forObject(executorProxy, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		// 分离代理对象链
		while (metaExecutor.hasGetter("h")) {
			Object object = metaExecutor.getValue("h");
			metaExecutor = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		}
		// 分离最后一个代理对象的目标类
		while (metaExecutor.hasGetter("target")) {
			Object object = metaExecutor.getValue("target");
			metaExecutor = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		}
		Object[] args = invocation.getArgs();
		return this.query(metaExecutor, args);
	}

	public <E> List<E> query(MetaObject metaExecutor, Object[] args) throws SQLException {
		MappedStatement ms = (MappedStatement) args[0];
		Object parameterObject = args[1];
		RowBounds rowBounds = (RowBounds) args[2];
		ResultHandler resultHandler = (ResultHandler) args[3];
		BoundSql boundSql = ms.getBoundSql(parameterObject);
		// 改写key的生成
		CacheKey cacheKey = createCacheKey(ms, parameterObject, rowBounds, boundSql);
		Executor executor = (Executor) metaExecutor.getOriginalObject();
		// 执行分页查询
		if (paged(ms, parameterObject)) {
			MetaObject metaObject = MetaObject.forObject(parameterObject, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
			PageInfo page = (PageInfo) metaObject.getValue("page");

			long start = System.currentTimeMillis();
			int totalCount = 0;  //总条数
			int totalPage = 0; //总页数
			// 查询总数
			String countSql = getCountSql(boundSql.getSql());
	        CacheKey pageCacheKey=createEcachePagetotal(ms,parameterObject,rowBounds,boundSql);
	        org.apache.ibatis.cache.Cache cache = ms.getCache();
	        if(cache!=null
	        		&&cache.getObject(pageCacheKey)!=null&&!ms.isFlushCacheRequired()){
	        	totalCount = (Integer) cache.getObject(pageCacheKey);
	        }else {
				PreparedStatement countStmt =null;
				Connection connection =null;
				ResultSet rs =null;
				try {
				    connection = ms.getConfiguration().getEnvironment().getDataSource().getConnection();
				    countStmt = connection.prepareStatement(countSql);
					BoundSql countBS = copyFromBoundSql(ms, boundSql, countSql);
					DefaultParameterHandler parameterHandler = new DefaultParameterHandler(ms, parameterObject, countBS);
					parameterHandler.setParameters(countStmt);
					rs = countStmt.executeQuery();

					if (rs.next()) {
						totalCount = rs.getInt(1);
					 if(cache!=null){
						cache.putObject(pageCacheKey, totalCount);
					   }
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}finally{
					try {
						if(rs!=null)
						rs.close();
						if(countStmt!=null)
						countStmt.close();
						if(connection!=null)
						connection.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
			
			if(totalCount == 0){
	    		totalPage = 0;
	    	}else if(totalCount > 0){
	    		//总条数小于每页显示的条数
	    		if(totalCount <= page.getPageSize()){
	    			totalPage = 1;
	    		}else{//总条数大于于每页显示的条数
		    		if(totalCount%page.getPageSize() == 0){ //如果取模为0（能够整除）
		    			totalPage = totalCount/page.getPageSize();
		    		}else{
		    			totalPage = totalCount/page.getPageSize() + 1;
		    		}
	    		}
	    	}
	        //设置PageInfo的总条数值
			page.setTotalCount(totalCount);
			//设置PageInfo的总页数值
			page.setTotalPage(totalPage);
			long cast = System.currentTimeMillis() - start;
			System.out.println("查询耗时:"+cast);
			// 分页查询
			String pageSql = buildPageSql(boundSql.getSql(), page);
			BoundSql newBoundSql = copyFromBoundSql(ms, boundSql, pageSql);
			MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
			ms = newMs;
			boundSql = newBoundSql;
		  }
		return executor.query(ms, parameterObject, rowBounds, resultHandler, cacheKey, boundSql);
	}

	
	/**
	 * 根据原Sql语句获取对应的查询总记录数的Sql语句
	 */
	private String getCountSql(String sql) {
		return "SELECT COUNT(*) FROM (" + sql + ") aliasForPage";
	}

	/**
	 * 复制MappedStatement对象
	 */
	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}

	/**
	 * 复制BoundSql对象
	 */
	private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
			String prop = mapping.getProperty();
			if (boundSql.hasAdditionalParameter(prop)) {
				newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
			}
		}
		return newBoundSql;
	}

	public Object plugin(Object target) {
		if (target instanceof CachingExecutor) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	public void setProperties(Properties properties) {

	}

	private boolean paged(MappedStatement ms, Object parameterObject) {
		MetaObject metaObject = MetaObject.forObject(parameterObject, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);

		if (metaObject.hasGetter(PageInfo.NO_NEED_PAGE)) {
			boolean noNeedPage = (Boolean) metaObject.getValue(PageInfo.NO_NEED_PAGE);
			if (noNeedPage) {
				return false;
			}
		}

		if (ms.getId().matches(pageSqlId) && metaObject.hasGetter("page")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 创建查询总条数
	 * @param ms
	 * @param parameterObject
	 * @param rowBounds
	 * @param boundSql
	 * @return
	 */
	public CacheKey createEcachePagetotal(MappedStatement ms,
			Object parameterObject, RowBounds rowBounds, BoundSql boundSql) {
		List<ParameterMapping> parameterMappings = boundSql
				.getParameterMappings();
		CacheKey cacheKey = new CacheKey();
		cacheKey.update(ms.getId());
		cacheKey.update(rowBounds.getOffset());
		cacheKey.update(rowBounds.getLimit());
		cacheKey.update(getCountSql(boundSql.getSql()));

		Map po = (Map) parameterObject;
		po.remove("page");
		MetaObject metaObject = MetaObject.forObject(po,
				DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		if (po != null && po.size() > 0) {
			TypeHandlerRegistry typeHandlerRegistry = ms.getConfiguration()
					.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				cacheKey.update(parameterObject);
			} else {
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						cacheKey.update(parameterMapping.getProperty());
						cacheKey.update(metaObject.getValue(propertyName));
					}
				}
			}
		}
		return cacheKey;
	}

	@SuppressWarnings("rawtypes")
	private CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql) {
		Configuration configuration = ms.getConfiguration();
		pageSqlId = defaultPageSqlId;

		CacheKey cacheKey = new CacheKey();
		cacheKey.update(ms.getId());
		cacheKey.update(rowBounds.getOffset());
		cacheKey.update(rowBounds.getLimit());
		cacheKey.update(boundSql.getSql());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

		MetaObject metaObject = MetaObject.forObject(parameterObject, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);

		if (parameterMappings.size() > 0 && parameterObject != null) {
			TypeHandlerRegistry typeHandlerRegistry = ms.getConfiguration().getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				cacheKey.update(parameterObject);
			} else {
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						cacheKey.update(parameterMapping.getProperty());
						cacheKey.update(metaObject.getValue(propertyName));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						cacheKey.update(boundSql.getAdditionalParameter(propertyName));
					}
				}
			}
		}
		// 当需要分页查询时，将page参数里的当前页和每页数加到cachekey里
		if (paged(ms, parameterObject)) {
			PageInfo page = (PageInfo) metaObject.getValue("page");

			if (null != page) {
				cacheKey.update(page.getCurrentPage());
				cacheKey.update(page.getPageSize());
			}
		}
		return cacheKey;
	}

	/**
	 * buildPageSql:拼装sql语句 <br/>
	 * 适用条件：支持mysql和oracle数据库<br/>
	 * 执行流程：内部调用<br/>
	 *
	 * @author:liqian 2017-12-6 14:49:36
	 * @param sql
	 *            sql语句
	 * @param page
	 *            分页信息
	 * @return String 拼装过后的sql语句
	 * @since JDK 1.7
	 */
	private String buildPageSql(String sql, PageInfo page) {
		if (page != null) {
			StringBuilder pageSql = new StringBuilder();
			if ("mysql".equals(dialect)) {
				// 调用mysql的拼装方法
				pageSql = buildPageSqlForMysql(sql, page);
			} else if ("oracle".equals(dialect)) {
				// 调用oracle的拼装方法
				pageSql = buildPageSqlForOracle(sql, page);
			} else {
				return sql;
			}
			return pageSql.toString();
		} else {
			return sql;
		}
	}

	/**
	 * buildPageSqlForMysql:mysql的分页语句 <br/>
	 * 适用条件：mysql数据库<br/>
	 * 执行流程：内部调用<br/>
	 * @author:liqian
	 * @param sql  sql语句
	 * @param page 分页信息
	 * @return StringBuilder 拼装过后的sql语句
	 * @since JDK 1.7
	 * 2017-12-6 14:48:31
	 */
	private StringBuilder buildPageSqlForMysql(String sql, PageInfo page) {
		StringBuilder pageSql = new StringBuilder(100);
		// 计算起始行
		String beginrow = String.valueOf((page.getCurrentPage() - 1) * page.getPageSize());
		pageSql.append(sql);
		pageSql.append(" limit " + beginrow + "," + page.getPageSize());
		return pageSql;
	}

	/**
	 * buildPageSqlForOracle:oracle分页方法 <br/>
	 * 适用条件：oracle数据库<br/>
	 * 执行流程：内部调用<br/>
	 *
	 * @author:liqian 2017-12-6 14:49:05
	 * @param sql
	 *            sql语句
	 * @param page
	 *            分页信息
	 * @return StringBuilder 拼装过后的sql语句
	 * @since JDK 1.7
	 */
	private StringBuilder buildPageSqlForOracle(String sql, PageInfo page) {
		StringBuilder pageSql = new StringBuilder(100);
		// 计算起始行
		String beginrow = String.valueOf((page.getCurrentPage() - 1) * page.getPageSize());
		// 计算截止行
		String endrow = String.valueOf(page.getCurrentPage() * page.getPageSize());
		pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
		// pageSql.append(sql);
		if (page.getSort() != null && page.getSort() != "") { // 判断是否存在排序参数
			if (sql.toLowerCase().lastIndexOf("order") == -1) { // 判断原sql是否存在排序信息
				pageSql.append(sql);
				pageSql.append(" order by " + page.getSort());
				if (page.getOrder() != null && page.getOrder() != "") {
					pageSql.append(" " + page.getOrder());
				}
			} else { // 存在
				int orderIndex = sql.toLowerCase().lastIndexOf("order");
				int byIndex = sql.toLowerCase().indexOf("by", orderIndex);
				String sqlLeft = sql.substring(0, byIndex + 2);
				pageSql.append(" " + sqlLeft);
				pageSql.append(" " + page.getSort());
				if (page.getOrder() != null && page.getOrder() != "") {
					pageSql.append(" " + page.getOrder());
				}
				String sqlRight = sql.substring(byIndex + 2);
				pageSql.append(" , " + sqlRight);
			}
		} else {
			pageSql.append(sql);
		}
		pageSql.append(" ) temp where rownum <= ").append(endrow);
		pageSql.append(") where row_id > ").append(beginrow);
		return pageSql;
	}

}

class BoundSqlSqlSource implements SqlSource {
	BoundSql boundSql;

	public BoundSqlSqlSource(BoundSql boundSql) {
		this.boundSql = boundSql;
	}

	public BoundSql getBoundSql(Object parameterObject) {
		return boundSql;
	}
}
