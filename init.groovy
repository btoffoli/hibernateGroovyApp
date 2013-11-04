@Grapes([
@Grab(group = 'org.hibernate', module = 'hibernate-annotations', version = '3.4.0.GA'),
//@Grab(group='org.hibernate', module='hibernate-core', version='4.2.6.Final'),
@Grab(group = 'org.slf4j', module = 'slf4j-simple', version = '1.4.2'),
//@Grab(group='hsqldb', module='hsqldb', version='1.8.0.7'),
@Grab(group = 'postgresql', module = 'postgresql', version = '8.4-701.jdbc4'),
@Grab(group = 'javassist', module = 'javassist', version = '3.4.GA'),
@Grab(group = 'org.reflections', module = 'reflections', version = '0.9.9-RC1')
])

package br.com.btoffoli

//import javax.persistence.*
//import org.hibernate.cfg.*
import br.com.btoffoli.conf.ConfigHibernate
import br.com.btoffoli.utils.UtilsReflection
import br.com.btoffoli.resource.Resource
import br.com.btoffoli.conf.ConfigApp
import org.hibernate.SessionFactory



ConfigApp.config()

// store some books
def session = Resource.sessionFactory.currentSession
def tx = session.beginTransaction()
Integer qnt = session.createQuery('select max(id) from TipoOcorrencia').list().first()
println qnt.class
//session.save(new TipoOcorrencia(nome:'Teste', sigla:'Sigla Teste 1'))
//session.save(new TipoOcorrencia(nome:'Teste2', sigla:'Sigla Teste 2'))

tx.commit()

// find some books
session = Resource.sessionFactory.currentSession
tx = session.beginTransaction()
def tiposOcorrencias = session.createQuery("from TipoOcorrencia").list()
println 'Found ' + tiposOcorrencias.size() + ' tiposOcorrencias:'
tiposOcorrencias.each { println it }
tx.commit()