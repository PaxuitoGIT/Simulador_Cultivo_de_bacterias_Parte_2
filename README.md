# Simulador para gestionar cultivos de bacterias parte 2

Link del repositorio: https://github.com/PaxuitoGIT/Simulador_Cultivo_de_bacterias_Parte_2

Edit lunes 20/5/24: Se ha corregido un error que no mostraba bien los detalles de la población cuando se creaba uno nuevo.

## Análisis y descripción de la aplicación

En esta parte, he trabajado en base al código ya creado en la parte 1, por lo que muchos de los añadidos son ampliaciones y mejoras al programa.

- Se ha actualizado para que las fechas sean variables y no un máximo de 30 días.
- Se ha actualizado la cantidad máxima de comida de 300 miligramos a 300.000 microgramos.
- Nuevo: Se han añadido 4 patrones diferentes siendo:
  
  Incremento-Decremento: Lo mismo que hace en la parte 1, empieza creciendo la comida hasta el día que se empieza a consumir. A partir de allí empieza a decrecer hasta la comida
  final asignada por el usuario.

  Constante: La cantidad de comida es siempre la misma

  Decrecimiento lineal: La cantidad de comida que empieza a disminuir es siempre la misma hasta llegar a la comida final.

  Alternativo: La comida se empieza a consumir día sí y día no hasta llegar a la comida final.

- Nuevo: Se ha añadido la opción de ordenar poblaciones en Ver Detalles Población por fecha inicial asociada a la población, nombre de la población y por número de bacterias de la población.
- Nuevo: Se ha añadido la simulación de Montecarlo aparte y podrás simular en una tabla de 20x20 sucesos aleatorios en el que dependiendo de la comida, las bacterias pueden morir,
  quedarse en el mismo sitio, moverse a otras celdas y comer. Si se deja el ratón encima de las celdas aparecerá la cantidad de comida. Se les han asignado un color a cada rango de bacterias en
  cada casilla siendo verde para más de 20 bacterias en una celda, morado si son entre 15 y 19, naranja si son entre 10 y 14, amarillo si son entre 5 y 9 y blanco si son 0.

  Para funcionar la simulación Montecarlo solo hace falta introducir los datos a la opción de arriba y una vez dado los datos, pinchar en simular Montecarlo las veces que quieras y se actualizarán
  los datos en tiempo real.

  ## Listado de fallos conocidos

  En la ventana del simulador Montecarlo, cuando se deja el ratón para ver la cantidad de comida en los bordes, el programa es capaz de volverse en blanco y con reiniciar el programa
  se soluciona de forma temporal hasta que se haga la misma acción. Lo recomendable es no dejar el ratón sobre las celdas que están en los bordes.

  Sigue el mismo error en el que si se abre el json de ejemplo que hay desde el jar, no lo leerá de forma correcta pero sí lo lee si se inicia desde el propio IntellIj.
  Sin embargo, si el jar crea su propio guardado json, sí leerá el suyo.

  No se ha podido implementar la función de reproducir bacterias cumpliendo ciertas condiciones.
