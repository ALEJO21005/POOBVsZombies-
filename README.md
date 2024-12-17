# POOBVsZombies


En este proyecto desarrollamos POOB Vs Zombies, una adaptación basada en el popular videojuego Plantas vs Zombies. El objetivo del juego es que el jugador utilice diferentes tipos de plantas para defenderse de hordas de zombies, cada uno con comportamientos y características particulares.

## Diseño y Modelado del Proyecto
Para planificar la estructura del proyecto, utilizamos la herramienta Astah para modelar las clases, interfaces y relaciones entre los elementos clave. Este diseño se basó en los principios de la programación orientada a objetos, asegurando un sistema modular, reutilizable y extensible.

## Clases principales
Clases abstractas: Plant y Zombie
Estas clases representan las entidades principales del juego y proporcionan una estructura base para los diferentes tipos de plantas y zombies:

### Plant
Define los comportamientos básicos de las plantas, como atacar o interactuar con otros elementos del tablero. Permite heredar y personalizar comportamientos para cada planta específica, como el Peashooter (lanzaguisantes) o el Sunflower (generador de soles).

### Zombie
Proporciona una estructura base para diferentes tipos de zombies, definiendo métodos para moverse y atacar. Esto permite heredar características y habilidades específicas, como mayor resistencia o velocidad.

### Board
Esta clase maneja el estado del tablero, representado como una matriz que asocia las posiciones de las plantas, zombies y otros elementos. Sus métodos permiten agregar, eliminar y consultar los elementos en el tablero de manera eficiente.

## Clase principal PlantsVsZombies
Este es el controlador del juego, encargado de manejar la lógica principal, como colocar plantas, gestionar los ataques entre plantas y zombies, recolectar soles y mantener el flujo del juego.

## Clases concretas

Plantas específicas:
Peashooter: Planta que lanza guisantes para atacar zombies.
Sunflower: Planta que genera soles para que el jugador compre más plantas.
Zombies específicos:
ZombieBasic: Zombie básico con comportamiento estándar.
ZombieBucketHead: Zombie con mayor resistencia debido a su cubeta.
ZombieConeHead: Zombie con resistencia inicial adicional gracias a su cono.
Clase Pea
Representa los proyectiles lanzados por plantas como el Peashooter. Su comportamiento principal es moverse por el tablero y dañar zombies.

### Interfaces Implementadas
GameEventListener
Esta interfaz maneja eventos específicos del juego, como ataques de plantas o muerte de zombies.

Propósito:

Mantener el juego modular y extensible, permitiendo la integración con sistemas de visualización o registro de eventos sin modificar la lógica central del juego.
Decisiones de diseño y su justificación
Uso de clases abstractas:
Permitieron definir comportamientos comunes entre zombies y plantas, simplificando la herencia y asegurando que las subclases cumplieran con los contratos establecidos en las clases base.

Separación de lógica en Board y PlantsVsZombies:

Board se encargó de mantener el estado físico del tablero, mientras que PlantsVsZombies manejó la lógica del juego. Esto garantizó una clara separación de responsabilidades.
Uso de interfaces:
La interfaz GameEventListener facilitó la comunicación entre los componentes del juego y cualquier sistema de eventos, como la interfaz gráfica.

Representación matricial del tablero:
Esta decisión simplificó la implementación de las interacciones entre plantas, zombies y guisantes, permitiendo una fácil detección de colisiones y movimientos.

Extensibilidad:
Gracias a las clases abstractas e interfaces, el sistema permite añadir nuevos tipos de plantas, zombies y comportamientos con facilidad.

Conclusión
El proyecto POOB Vs Zombies integra los principios fundamentales de la programación orientada a objetos con un diseño modular y extensible. Las decisiones tomadas garantizan un código organizado, fácil de mantener y preparado para futuras expansiones.
