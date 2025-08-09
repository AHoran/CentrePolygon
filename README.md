Centre Polygon
=============

This is a java implementation of algorithm to find the centre of an arbitrary polygon. Where "centre" is defined as the centre of the largest circle that can be drawn within the polygon.

It is based on the process described in this paper http://arxiv.org/ftp/arxiv/papers/1212/1212.3193.pdf *An Efficient Algorithm to Calculate the Center of the Biggest Inscribed Circle in an Irregular Polygon* by *OSCAR MARTINEZ*. See https://github.com/omtinez/CenterPolygon for the version by the paper's author in C.

## How to Build, Run, and Test (Maven)

This project uses [Maven](https://maven.apache.org/) for builds and dependency management. You can open it in any major Java IDE (IntelliJ IDEA, Eclipse, VS Code, NetBeans) or use the command line.

### Build
```
mvn clean package
```

### Run
To run the main class (replace `CentrePolygon` with your actual main class if needed):
```
mvn exec:java -Dexec.mainClass="CentrePolygon"
```

### Test
Run all unit tests:
```
mvn test
```

### IDE Support
Just open the project folder (containing `pom.xml`) in your IDE. The IDE will automatically detect and configure the Maven project.

Alternatives
============
See also this newer approach: https://github.com/mapbox/polylabel
