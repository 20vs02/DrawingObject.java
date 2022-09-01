public class Cube {

    /**
     * Gibt die Eckpunkte eines Einheitswürfels zurück (Zentrum bei (0,0,0)).
     * Rechtshändiges Koordinatensystem: x-Achse nach rechts, y-Achse nach oben, z-Achse aus Bildschirm heraus
     */
    private static double[][] verticesOfUnitCube() {
        double[][] verticesHexahedron = {{-0.5, -0.5, -0.5},
                                         {-0.5, -0.5,  0.5},
                                         {-0.5,  0.5, -0.5},
                                         {-0.5,  0.5,  0.5},
                                         { 0.5, -0.5, -0.5},
                                         { 0.5, -0.5,  0.5},
                                         { 0.5,  0.5, -0.5},
                                         { 0.5,  0.5,  0.5}};
        return verticesHexahedron;
    }


    /**
     * Multipliziert zwei Matrizen und gibt das Produkt als neue Matrix zurück.
     * siehe https://en.wikipedia.org/wiki/Matrix_multiplication#Definition
     */
    private static double[][] matrixMultiplication(double[][] matrixA, double[][] matrixB) {
        double[][] product = new double[matrixA.length][matrixB[0].length];

        for (int i = 0; i < product.length; i++) {
            for (int j = 0; j < product[0].length; j++) {
                for (int k = 0; k < matrixA[0].length; k++) {
                    product[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        return product;
    }

    /**
     * Berechnet eine Rotationsmatrix mit Winkel alpha für eine Drehung um die x-Achse.
     * siehe https://de.wikipedia.org/wiki/Drehmatrix#Drehmatrizen_des_Raumes_%E2%84%9D%C2%B3
     */
    private static double[][] rotationXMatrix(double alpha) {
        return new double[][]{{1,               0,                0},
                              {0, Math.cos(alpha), -Math.sin(alpha)},
                              {0, Math.sin(alpha),  Math.cos(alpha)}};
    }

    /**
     * Berechnet eine Rotationsmatrix mit Winkel alpha für eine Drehung um die y-Achse.
     * siehe https://de.wikipedia.org/wiki/Drehmatrix#Drehmatrizen_des_Raumes_%E2%84%9D%C2%B3
     */
    private static double[][] rotationYMatrix(double alpha) {
        return new double[][]{{Math.cos(alpha), 0, -Math.sin(alpha)},
                              {              0, 1,                0},
                              {Math.sin(alpha), 0,  Math.cos(alpha)}};
    }

    /**
     * Zeichnet eine 3D-Linie mit orthogonaler Projektion, wobei sie heller ist, je weiter der Mittelpunkt entfernt ist.
     */
    private static void drawShadedLine(double[] start, double[] end) {
        // Gehe davon aus, dass die Linienmittelpunkte nicht weiter weg als -0.75 und nicht näher als 0.75 sind.
        // Berechne, wo sich der Mittelpunkt in diesem Interval befindet.
        // weiter weg = mehr Transparenz = heller
        double nearestY = 1.5;
        double furthestY = -1.5;
        double meanDistance = (end[2] + start[2]) / 2;
        // Stellt sicher, dass der Transparenzwert innerhalb von [0, 1] ist
        double transparency = Math.min(1, Math.max(0, (meanDistance - nearestY) / (furthestY - nearestY)));

        // Setze die Farbe zum Zeichnen der nächsten Linie.
        // Eine Farbe wird in Form von drei Werten zwischen 0 und 255 angegeben, die für den Anteil Rot, Grün und Blau stehen.
        // Für ein Grau müssen alle drei Farbwerte denselben Wert haben. 255 = weiß, 0 = schwarz.
        int gray = (int) (transparency * 255);
        StdDraw.setPenColor(gray, gray, gray);

        StdDraw.line(start[0], start[1], end[0], end[1]);
    }

    /**
     * Zeichnet die 12 Kanten eines 3D-Würfels mit orthogonaler Projektion.
     * Kanten, die weiter entfernt sind, werden heller gezeichnet.
     * orthogonale Projektion: z-Koordinate wird ignoriert
     */
    private static void drawCube(double[][] verticesHexahedron) {
        drawShadedLine(verticesHexahedron[0], verticesHexahedron[1]);
        drawShadedLine(verticesHexahedron[0], verticesHexahedron[2]);
        drawShadedLine(verticesHexahedron[0], verticesHexahedron[4]);
        drawShadedLine(verticesHexahedron[1], verticesHexahedron[3]);
        drawShadedLine(verticesHexahedron[1], verticesHexahedron[5]);
        drawShadedLine(verticesHexahedron[2], verticesHexahedron[3]);
        drawShadedLine(verticesHexahedron[2], verticesHexahedron[6]);
        drawShadedLine(verticesHexahedron[3], verticesHexahedron[7]);
        drawShadedLine(verticesHexahedron[4], verticesHexahedron[5]);
        drawShadedLine(verticesHexahedron[4], verticesHexahedron[6]);
        drawShadedLine(verticesHexahedron[5], verticesHexahedron[7]);
        drawShadedLine(verticesHexahedron[6], verticesHexahedron[7]);
    }

    /**
     * Rotiert das gegebene Objekt um die x-Achse und anschließend um die y-Achse.
     * Gibt dann die neuen Eckpunkte des rotierten Objekts zurück.
     */
    private static double[][] rotateObject(double[][] vertices, double angleX, double angleY) {
        double[][] xRotated = matrixMultiplication(vertices, rotationXMatrix(angleX));
        double[][] xyRotated = matrixMultiplication(xRotated, rotationYMatrix(angleY));
        return xyRotated;
    }

    public static void main(String[] args) {
        StdDraw.setScale(-1, 1);

        double[][] cube = verticesOfUnitCube();

        // unendliche Animationsschleife
        for(double angle = 0; ; angle += 0.1) {
            double[][] cubeRotated = rotateObject(cube, angle, angle / 10);
            drawCube(cubeRotated);

            // Bild für 100 Millisekunden anzeigen, dann nächstes Bild berechnen
            StdDraw.show(30);
            StdDraw.clear();
        }
    }
}
