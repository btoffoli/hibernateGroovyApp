package br.com.btoffoli

import javax.persistence.*
import org.hibernate.cfg.*
import br.com.btoffoli.conf.Config
import br.com.btoffoli.dominio.TipoOcorrencia


// javax.transaction jta.jar added manually to ivy repo
@Grapes([
    @Grab(group='org.hibernate', module='hibernate-annotations', version='3.4.0.GA'),
    //@Grab(group='org.hibernate', module='hibernate-core', version='4.2.6.Final'),
    @Grab(group='org.slf4j', module='slf4j-simple', version='1.4.2'),
    //@Grab(group='hsqldb', module='hsqldb', version='1.8.0.7'),
    @Grab(group='postgresql', module='postgresql', version='8.4-701.jdbc4'),
    @Grab(group='javassist', module='javassist', version='3.4.GA'),
])




def factory = new Config().configureHibernate(
    [TipoOcorrencia]
).buildSessionFactory()

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