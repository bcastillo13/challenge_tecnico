# challenge_tecnico

## Ejecución app xmen.mutant.detector

#### Prerrequisitos
Instalar Docker y Docker Compose

- https://docs.docker.com/engine/install/

- https://docs.docker.com/compose/install/

### Ejecución
En la carpeta Docker se encuentra un archivo de configuración de Docker Compose (docker-compose.yaml) el cual configura dos contenedores uno con la aplicación xmen.mutant.detector(imágen publicada en Docker hub) y el segundo con una base de datos PostgreSQL.

Dentro del directorio 'Docker' ejecutar en la linea de comandos del entorno donde se instalo Docker:

**docker compose up -d**

#### Nota:

El proyecto se puede ejecutar de igual manera empaquetando la aplicación con maven (mvn clean package) y posteriormente ejecutando la aplicación mediante docker compose. Todo dentro del directorio 'challenge_tecnico/xmen.mutant.detector/'

Finalmente para acceder a la API se hace mediante el host y el puerto 80. Los servicios expuestos por la API a continuación:

**POST** http://host:80/mutant

#### Body JSON:

**{
"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}**

Return HTTP 200(mutante) o HTTP 403(humano)

**GET** http://host:80/stats

Return **{“count_mutant_dna”:40, “count_human_dna”:100: “ratio”:0.4}**








