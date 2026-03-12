# 📊 Portafolio de Probabilidad y Estadística — Guía de Integración

## Estructura del Proyecto

Coloca cada archivo en su ruta correspondiente dentro de tu proyecto Maven/IntelliJ:

```
src/
├── main/
│   ├── java/
│   │   └── com/estadistica/portafolio/         ← cambia el paquete si necesitas
│   │       ├── MainApp.java
│   │       ├── controller/
│   │       │   └── MainController.java
│   │       └── topics/
│   │           ├── BaseTopicWindow.java         ← clase base (no modificar)
│   │           └── ProbabilidadClasicaWindow.java   ← ejemplo de tema
│   └── resources/
│       └── com/estadistica/portafolio/
│           ├── fxml/
│           │   └── main-view.fxml
│           └── styles/
│               └── main.css
```

---

## ✏️ Tus Datos Personales

Edita las constantes al inicio de `MainApp.java`:
```java
public static final String NOMBRE_ALUMNO = "Tu Nombre Aquí";
public static final String GRUPO         = "Grupo X";
public static final String SEMESTRE      = "2025-1";
```

---

## ➕ Cómo Agregar un Nuevo Tema (3 pasos)

### Paso 1 — Crear la clase del tema
Crea `MiNuevoTemaWindow.java` en el paquete `topics/`, extendiendo `BaseTopicWindow`:

```java
public class MiNuevoTemaWindow extends BaseTopicWindow {
    @Override public String getTitulo()      { return "Mi Nuevo Tema"; }
    @Override public String getNombreTema()  { return "Nombre Completo del Tema"; }
    @Override public String getIcono()       { return "fas-chart-bar"; /* ícono Ikonli */ }
    @Override public String getDefinicion()  { return "Aquí va la definición del concepto..."; }
    @Override public String getFormula()     { return "P(A) = n(A) / n(S)"; /* o null si no hay */ }
    @Override public String getDescFormula() { return "Donde n(A) son los casos favorables..."; }

    @Override
    public Node buildDemoSection() {
        // Construye aquí tu UI interactiva
        VBox demo = new VBox(12);
        // ...agrega campos, botones, resultado...
        return demo;
    }
}
```

### Paso 2 — Agregar el botón en `main-view.fxml`
Dentro del bloque `<!-- ZONA DE TEMAS -->`, duplica el bloque `<Button>`:

```xml
<Button fx:id="btnTema2" styleClass="nav-button" maxWidth="Infinity" onAction="#abrirTema2">
    <graphic>
        <HBox spacing="10" alignment="CENTER_LEFT">
            <FontIcon iconLiteral="fas-chart-bar" iconSize="16" iconColor="#8b8fa8"/>
            <Label text="Mi Nuevo Tema" mouseTransparent="true"/>
        </HBox>
    </graphic>
</Button>
```

### Paso 3 — Registrar en `MainController.java`
Agrega el campo y el método:

```java
@FXML private Button btnTema2;   // ← nuevo campo

@FXML
private void abrirTema2() {
    setActiveButton(btnTema2);
    new MiNuevoTemaWindow().show();
}
```

¡Listo! El tema aparece en el menú y en el grid del dashboard automáticamente.

---

## 🎨 Íconos Disponibles (Ikonli FontAwesome 5)

| Código              | Descripción      |
|---------------------|------------------|
| `fas-home`          | Casa / Inicio    |
| `fas-chart-bar`     | Gráfica de barras|
| `fas-chart-pie`     | Gráfica circular |
| `fas-dice`          | Dados            |
| `fas-calculator`    | Calculadora      |
| `fas-bell`          | Campana (normal) |
| `fas-percent`       | Porcentaje       |
| `fas-superscript`   | Fórmula          |
| `fas-table`         | Tabla de datos   |
| `fas-random`        | Aleatoriedad     |
| `fas-infinity`      | Infinito         |
| `fas-code`          | Código           |

---

## 📦 Dependencias requeridas en `pom.xml`

```xml
<!-- BootstrapFX -->
<dependency>
    <groupId>org.kordamp.bootstrapfx</groupId>
    <artifactId>bootstrapfx-core</artifactId>
    <version>0.4.0</version>
</dependency>

<!-- ControlsFX -->
<dependency>
    <groupId>org.controlsfx</groupId>
    <artifactId>controlsfx</artifactId>
    <version>11.1.2</version>
</dependency>

<!-- Ikonli Core + FontAwesome 5 -->
<dependency>
    <groupId>org.kordamp.ikonli</groupId>
    <artifactId>ikonli-javafx</artifactId>
    <version>12.3.1</version>
</dependency>
<dependency>
    <groupId>org.kordamp.ikonli</groupId>
    <artifactId>ikonli-fontawesome5-pack</artifactId>
    <version>12.3.1</version>
</dependency>
```