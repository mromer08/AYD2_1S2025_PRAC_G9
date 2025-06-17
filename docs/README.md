# Practica única - Documentacion Sistema de Reproducción de Música
## 1. Core del Negocio
### Descripcion
El sistema ofrece reproducción personalizada y gestión eficiente de música en un entorno digital, con enfoque en una experiencia visual adaptable, extensibilidad modular y soporte para futuras integraciones.

### Stakeholders
- **Usuario final:** Persona que utiliza el reproductor para escuchar música y gestionar sus listas de reproducción.
- **Administrador:** Responsable de gestionar la biblioteca musical, incluyendo la carga, edición y eliminación de canciones.
- **Disqueras:** Entidades proveedoras del contenido musical que buscan distribuir y promocionar su música a través del sistema.

### Diagrama CDU de Alto Nivel
![CDU_alto_nivel](./assets/cdu/CDU-alto-nivel.png)

### Primera Descomposición
![primera_descomposicion](./assets/cdu/primera-descomposicion.png)

## 2. Casos de Uso Expandidos
### Diagramas
![cdu01](./assets/cdu/cdu01.png)

![cdu02](./assets/cdu/cdu02.png)

![cdu03](./assets/cdu/cdu03.png)

![cdu04](./assets/cdu/cdu04.png)

### Listado
- **CDU01 - Experiencia de usuario**
- **CDU02 - Gestionar lista de reproduccion** 
- **CDU03 - Gestionar reproduccion de musica**
- **CDU04 - Gestionar biblioteca de canciones**

## 3. Drivers arquitectónicos
### Requerimientos funcionales críticos (RF)

- **RF01 Cambio de tema visual:** El sistema debe permitir al usuario alternar entre temas visuales como modo claro y oscuro.
- **RF02 Reproducir canción:** El usuario debe poder iniciar la reproducción de una canción seleccionada.
- **RF03 Buscar canción:** El sistema debe permitir buscar canciones por nombre, artista o palabras clave.
- **RF04 Mostrar información de la canción:** El sistema debe mostrar título, artista y duración de la canción actual.
- **RF05 Crear lista de reproducción:** El usuario debe poder crear nuevas listas de reproducción personalizadas.
- **RF06 Editar lista de reproducción:** El usuario debe poder agregar, eliminar o reordenar canciones dentro de una lista de reproducción.
- **RF07 Cargar canciones a la biblioteca:** El administrador debe poder agregar nuevas canciones a la biblioteca del sistema.
- **RF08 Eliminar canciones de la biblioteca:** El administrador debe poder eliminar canciones existentes del catálogo musical.
- **RF09 Editar canciones de la biblioteca:** El administrador debe poder modificar los metadatos de las canciones (título, artista, etc.).

### Requisitos No Funcionales (RNF)

### Requisitos de Restricción (Drivers de Restricción)

## 4. Matrices de Trazabilidad
### Stakeholders vs Requerimientos

### Stakeholders vs CDU

### Requerimientos vs CDU