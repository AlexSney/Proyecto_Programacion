# 🔐 Sistema de Autenticación en Consola (Java)

Este es un proyecto educativo de consola en **Java** que implementa un sistema básico de **Inicio de Sesión (Login)** y **Registro de Usuarios**. El diseño del proyecto sigue el patrón de separación de responsabilidades en diferentes clases (`App` y `Controlador`), facilitando su comprensión, mantenimiento y futura expansión (como la integración con una tienda de videojuegos o bases de datos).

---

## 📂 Estructura del Proyecto

El código está dividido de la siguiente manera:

*   **`App.java`**: Es la clase principal y el punto de entrada del programa. Se encarga de instanciar el controlador e iniciar el flujo.
*   **`ControladorLogin.java`**: Contiene la lógica del negocio. Gestiona el registro de usuarios, las credenciales, el control de sesiones activas, los menús interactivos y la lectura de datos por consola.


## 🛠️ Tecnologías y Requisitos

*   **Lenguaje:** Java (JDK 8 o superior).
*   **Herramientas:** Cualquier IDE (VS Code, IntelliJ IDEA, Eclipse, NetBeans) o simplemente la terminal de comandos de tu sistema operativo.
*   **Almacenamiento:** Colecciones de Java en memoria (`HashMap`). *Nota: Los datos se pierden al cerrar la consola.*

---

## 🚀 Cómo Ejecutar la Aplicación

### Opción 1: Desde la Terminal (Consola de comandos)

1. Abre tu terminal y navega hasta la carpeta donde guardaste los archivos `.java`.
2. Compila ambos archivos con el siguiente comando:
   ```bash
   javac App.java ControladorLogin.java
