package br.com.btoffoli.conf

import javax.persistence.*
import org.hibernate.cfg.*


class Config {
	def props = [
	    //"hibernate.dialect": "org.hibernate.dialect.HSQLDialect",
	    "hibernate.dialect": "org.hibernate.dialect.PostgreSQLDialect",
	    //"hibernate.connection.driver_class": "org.hsqldb.jdbcDriver",
	    "hibernate.connection.driver_class": "org.postgresql.Driver",
	    //"hibernate.connection.url": "jdbc:hsqldb:mem:demodb",
	    "hibernate.connection.url": "jdbc:postgresql://localhost/tide",
	    "hibernate.connection.username": "geocontrol",
	    "hibernate.connection.password": "geo007",
	    "hibernate.connection.pool_size": "1",
	    "hibernate.connection.autocommit": "true",
	    "hibernate.cache.provider_class": "org.hibernate.cache.NoCacheProvider",
	    "hibernate.hbm2ddl.auto": "none",
	    "hibernate.show_sql": "true",
	    "hibernate.transaction.factory_class": "org.hibernate.transaction.JDBCTransactionFactory",
	    "hibernate.current_session_context_class": "thread"
	]

	def configureHibernate(List<Class> classes) {
	    
	    def config = new AnnotationConfiguration()
	    .setNamingStrategy(new ImprovedNamingStrategy(){

		    /**
		     * Called only if a <class> element does not specify a table name
		     */
		    @Override
		    public String classToTableName(String className) {
		        //this will transform it to the name to use.
		        //return splitCamelCase(StringHelper.unqualify(className));
		        return splitCamelCase(className)
		    }
		    
		    /**
		     * Called when the table name is specified. 
		     */
		    @Override
		    public String tableName(String tableName) {
		        //No action taken.provided table name is used.
		        //If necessary validations can be performed here to ensure that the table name is valid.
		        //Or better still simply throw an exception to prevent any developer providing table names
		        return tableName;
		    }
		    
		    /**
		     * Called only if a column name is provided
		     */
		    @Override
		    public String columnName(String columnName) {
		        //throw new RuntimeException("You are not allowed to set the column property!!!");        
		        return splitCamelCase(columnName).toUpperCase();
		    }
		    
		    /**
		     * Called when a column name is not provided
		     */
		    @Override
		    public String propertyToColumnName(String propertyName) {       
		        return splitCamelCase(propertyName).toUpperCase();
		    }
		    
		    /**
		     * Method will convert a Camel Case word into a word where the 
		     * individual fields are separated by _
		     * @param cameCaseWord
		     * @return {@link String}
		     * code used from :http://stackoverflow.com/questions/2559759/
		     *     how-do-i-convert-camelcase-into-human-readable-names-in-java
		     */
		    private String splitCamelCase(String cameCaseWord) {
		        return cameCaseWord.replaceAll(String.format(
		                "%s|%s|%s",
		                "(?<=[A-Z])(?=[A-Z][a-z])",
		                "(?<=[^A-Z])(?=[A-Z])",
		                "(?<=[A-Za-z])(?=[^A-Za-z])")
		            , "_")
		    }

	    })
	    

	    props.each { k, v -> config.setProperty(k, v) }
	    
	    classes.each{ Class clazz ->
	    	config.addAnnotatedClass(clazz)
	    }
	    return config
	}
}