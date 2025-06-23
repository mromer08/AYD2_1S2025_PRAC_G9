# Music Player App

Este proyecto es una aplicación desarrollada en **Java 21** utilizando **JavaFX**, **Hibernate** y **PostgreSQL** como base de datos.

## 🛠 Requisitos

Antes de ejecutar el proyecto, asegúrate de tener lo siguiente instalado en tu sistema:

- [Java 21](https://jdk.java.net/21/)
- [Apache Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/) (con un usuario y base de datos configurados)

## ⚙️ Configuración

Debes configurar la conexión a la base de datos editando el [hibernate.cfg.xml](src/main/resources/hibernate.cfg.xml):


Dentro de este archivo, asegúrate de que las siguientes propiedades coincidan con tu entorno local:

```xml
<property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/music_player</property>
<property name="hibernate.connection.username">postgres</property>
<property name="hibernate.connection.password">Nbpjxdxd%2</property>
```

> Se deben ingresar las credenciales correspondientes para quien ejecute la aplicacion

## ▶️ Ejecución
Para ejecutar la aplicación, utiliza el siguiente comando desde la raíz del proyecto:

```bash
# Esto compilará el proyecto y lanzará la interfaz JavaFX.
mvn clean javafx:run
```