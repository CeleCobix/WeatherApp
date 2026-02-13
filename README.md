## 🌤️ Weather App

![Banner](https://github.com/user-attachments/assets/7fe64c33-0c15-4852-9b4b-7585d00e8dd9)

Weather App es una aplicación nativa de Android que ofrece información meteorológica detallada y precisa utilizando la API de Open-Meteo. La aplicación obtiene automáticamente tu ubicación actual y muestra el pronóstico del clima en tiempo real, incluyendo temperatura, humedad, velocidad del viento, índice UV, calidad del aire y pronósticos por hora y por día.

## Características

- **Información meteorológica en tiempo real**:
  - Temperatura actual y sensación térmica
  - Humedad
  - Velocidad del viento
  - Índice UV
  - Precipitación actual
  - Calidad del aire
- **Pronóstico por horas**: Visualiza el clima para las próximas 24 horas
- **Pronóstico semanal**: Consulta el pronóstico para los próximos 7 días con temperaturas máximas y mínimas

## Tecnologías Utilizadas

- **Kotlin**: Lenguaje de programación principal
- **Jetpack Compose**: Framework moderno de UI declarativa
- **Retrofit**: Cliente HTTP para consumir la API REST

## Estructura del Proyecto

```
weatherapp/
├── data/                    # Capa de datos
│   ├── api/                 # Definiciones de API (Retrofit)
│   ├── model/               # Modelos de datos
│   └── repository/          # Repositorios para acceso a datos
│
├── di/                      # Inyección de dependencias
│
├── presentation/            # Capa de presentación
│   ├── ui/
│   │   ├── components/      # Componentes reutilizables de UI
│   │   └── theme/           # Tema y estilos de la app
│   └── viewmodel/           # ViewModels
│
└── util/                    # Utilidades y helpers
```

## Instalación

1. **Clona el repositorio**:
```bash
git clone https://github.com/tu-usuario/weather-app.git
cd weather-app
```

2. **Abre el proyecto en Android Studio**:
   - Abre Android Studio
   - Selecciona "Open an Existing Project"
   - Navega hasta la carpeta del proyecto y ábrela

3. **Sincroniza las dependencias**:
   - Android Studio sincronizará automáticamente las dependencias de Gradle
   - Si no lo hace, haz clic en "Sync Project with Gradle Files"

4. **Configura un dispositivo**:
   - Conecta un dispositivo Android físico con depuración USB habilitada, o
   - Crea un emulador de Android desde AVD Manager

5. **Ejecuta la aplicación**:
   - Haz clic en el botón "Run" en Android Studio
   - Selecciona tu dispositivo/emulador
   - Espera a que la aplicación se compile e instale

## Sobre la API

Esta aplicación utiliza la **Open-Meteo API**, un servicio gratuito y de código abierto que proporciona datos meteorológicos precisos sin necesidad de clave API.

**Endpoint utilizado:**
```
https://api.open-meteo.com/v1/forecast
```

**Documentación completa:** [https://open-meteo.com/en/docs](https://open-meteo.com/en/docs)

## Créditos

- **Iconos del clima**: Los iconos meteorológicos utilizados en esta aplicación fueron creados por [Basmilius](https://github.com/basmilius) y están disponibles en su repositorio [Weather Icons](https://github.com/basmilius/weather-icons). Agradecida por su excelente trabajo ✨.

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.

## Contacto

Si tienes alguna pregunta o sugerencia sobre este proyecto, no dudes en abrir un issue o contactar al desarrollador.
