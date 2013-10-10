package demo

import javax.persistence.*
import org.hibernate.cfg.*


// javax.transaction jta.jar added manually to ivy repo
@Grapes([
    @Grab(group='org.hibernate', module='hibernate-annotations', version='3.4.0.GA'),
    //@Grab(group='org.hibernate', module='hibernate-core', version='4.2.6.Final'),
    @Grab(group='org.slf4j', module='slf4j-simple', version='1.4.2'),
    //@Grab(group='hsqldb', module='hsqldb', version='1.8.0.7'),
    @Grab(group='postgresql', module='postgresql', version='8.4-701.jdbc4'),
    @Grab(group='javassist', module='javassist', version='3.4.GA'),
])
@Entity class TipoOcorrencia {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    Long version = 0

    Boolean habilitado = true
    Date dataHoraCriacao = new Date()
    Date dataHoraAtualizacao = dataHoraCriacao

    String nome
    String sigla

    Boolean georeferenciavel = false
    Boolean transferivel = false

    String toString() { "<${this.class} - ${this.id} - ${this.nome}>" }
}

def hibProps = [
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

def configureHibernate(props) {
    
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
    config.addAnnotatedClass(TipoOcorrencia)
    return config
}

def factory = configureHibernate(hibProps).buildSessionFactory()

// store some books
def session = factory.currentSession
def tx = session.beginTransaction()
Integer qnt = session.createQuery('select max(id) from TipoOcorrencia').list().first()
println qnt.class
//session.save(new TipoOcorrencia(nome:'Teste', sigla:'Sigla Teste 1'))
//session.save(new TipoOcorrencia(nome:'Teste2', sigla:'Sigla Teste 2'))

tx.commit()

// find some books
session = factory.currentSession
tx = session.beginTransaction()
def tiposOcorrencias = session.createQuery("from TipoOcorrencia").list()
println 'Found ' + tiposOcorrencias.size() + ' tiposOcorrencias:'
tiposOcorrencias.each { println it }
tx.commit()