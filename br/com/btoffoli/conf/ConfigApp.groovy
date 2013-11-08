package br.com.btoffoli.conf

/**
 * Created with IntelliJ IDEA.
 * User: btoffoli
 * Date: 03/11/13
 * Time: 10:21
 * To change this template use File | Settings | File Templates.
 */
import br.com.btoffoli.utils.UtilsReflection
import br.com.btoffoli.resource.Resource


class ConfigApp {

    static void config() {
        Resource.sessionFactory = new ConfigHibernate().config()
    }
}
