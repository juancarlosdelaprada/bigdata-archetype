package build

import java.io.{File, FileWriter}
import java.util.Properties

import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity
import org.slf4j.{Logger, LoggerFactory}


object Service {
     def log : Logger = LoggerFactory.getLogger( Service.getClass )

     def main(args: Array[String]): Unit = {

      // 1. Inicialización del motor de velocity
      val properties = new Properties
      properties.setProperty("resource.loader", "class")
      properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader")
      Velocity.init(properties)

      // 2. Inicialización del contexto de velocity
      val context = new VelocityContext
      context.put("app-name", new String(BuildInfo.name))

      try {

        // 3. Recuperar plantilla de ruta específica
        val template = Velocity.getTemplate("templates/servicio.vm")

        // 4. Obtención del resultado
        val file = new File(s"build/\${BuildInfo.name}.service")
        file.getParentFile.mkdirs

        val writer = new FileWriter(file)

        template.merge(context, writer)

        writer.flush()
        writer.close()
      }
      catch {
        case e: Exception =>
          log.error(e.getMessage)
      }
  }
}
