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
   
# 🎮 NEXUSGAMES — Plataforma de Distribución Digital de Videojuegos

Proyecto académico en Java — Consola — POO — Arquitectura por capas

---

## 📁 Estructura del Proyecto

```
NexusGames/
│
├── src/
│   ├── App.java                    ← Punto de entrada principal
│   │
│   ├── models/                     ← Modelos de datos (POO pura)
│   │   ├── User.java
│   │   ├── Game.java
│   │   └── Purchase.java
│   │
│   ├── services/                   ← Lógica de negocio y validaciones
│   │   ├── AuthService.java
│   │   ├── GameService.java
│   │   ├── WalletService.java
│   │   └── PurchaseService.java
│   │
│   ├── controllers/                ← Menús e interacción con el usuario
│   │   ├── AuthController.java
│   │   ├── StoreController.java
│   │   ├── LibraryController.java
│   │   └── AdminController.java
│   │
│   └── utils/                      ← Herramientas de apoyo
│       ├── FileManager.java        ← Lectura/escritura de TXT
│       ├── InputHelper.java        ← Lectura segura del teclado
│       └── Printer.java            ← Interfaz visual de consola
│
├── data/                           ← Archivos de persistencia (se crean solos)
│   ├── users.txt
│   ├── games.txt
│   └── purchases.txt
│
├── run.sh                          ← Compilar y ejecutar en Linux/Mac
├── run.bat                         ← Compilar y ejecutar en Windows
└── README.md
```

---

## 🚀 Cómo ejecutar

### Linux / Mac
```bash
chmod +x run.sh
./run.sh
```

### Windows
Doble clic en `run.bat` o desde CMD:
```
run.bat
```

### Manual (cualquier sistema)
```bash
# Desde la carpeta NexusGames/
mkdir out
javac -d out -sourcepath src src/App.java src/models/*.java src/utils/*.java src/services/*.java src/controllers/*.java
java -cp out App
```

---

## 🔑 Credenciales por defecto

| Usuario | Contraseña | Rol   |
|---------|-----------|-------|
| admin   | admin123  | ADMIN |

---

## 🎯 Módulos del sistema

### 🔐 Autenticación
- Registro con validación de duplicados y campos vacíos
- Login con validación de credenciales
- Sistema de roles: USER / ADMIN

### 🛒 Tienda
- Ver catálogo completo
- Buscar por nombre (búsqueda parcial)
- Filtrar por categoría
- Ver detalles y comprar (con todas las validaciones)

### 💰 Wallet
- Consultar saldo
- Recargar saldo (máx $500 por operación)
- Descuento automático al comprar

### 📚 Biblioteca
- Ver juegos comprados
- Historial con fecha de compra
- Sistema de favoritos (marcar/desmarcar)

### 👑 Panel Admin
- Agregar / modificar / eliminar juegos
- Ver lista de usuarios
- Ver historial de ventas
- Estadísticas con conteo por juego (HashMap)

---

## 🧠 Conceptos de POO aplicados

| Concepto         | Dónde se usa                              |
|-----------------|-------------------------------------------|
| Encapsulamiento  | Todos los modelos (private + getters)     |
| Constructores    | User, Game, Purchase                      |
| ArrayList        | Listas de users, games, purchases         |
| HashMap          | Estadísticas de ventas en AdminController |
| Modularidad      | Separación en capas controllers/services  |
| Recursividad     | InputHelper.leerEntero / leerDecimal      |
| Autómatas        | Bucles de menú con switch (estados)       |

---

## 💾 Formato de archivos TXT

**users.txt**
```
admin|admin123|9999.0|ADMIN
juan|pass1234|50.0|USER
```

**games.txt**
```
1|Elden Ring|59.99|RPG|15|M
2|Minecraft|26.99|Sandbox|50|E
```

**purchases.txt**
```
juan|1|Elden Ring|59.99|2025-06-01|false
juan|2|Minecraft|26.99|2025-06-02|true
```
