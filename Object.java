public class Object{


    private static double[][] Points() {
        double[][] points = {{ 1, 0, 0}, //013
                             { 0, 1, 0}, //102
                             { -1, 0, 0}, //213
                             { 0, -1, 0},

                             { 1, 0, 1},
                             { 0, 1, 1},
                             { -1, 0, 1},
                             { 0, -1, 1}, // 7

                             { 1, 0, -1}, // 8
                             { 0, 1, -1},
                             { -1, 0, -1},
                             { 0, -1, -1}};
        return points;
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


  // private static double[][] rotationYMatrix(double alpha) {
  //   return new double[][] = {{Math.cos(alpha), 0, -Math.sin(alpha)},
  //                            {              0, 1,                0},
  //                            {Math.sin(alpha), 0,  Math.cos(alpha)}};
  //  }


    /**
     * Zeichnet eine 3D-Linie mit orthogonaler Projektion, wobei sie heller ist, je weiter der Mittelpunkt entfernt ist.
     */
    private static void drawShadedLine(double[] start, double[] end) {
        // Gehe davon aus, dass die Linienmittelpunkte nicht weiter weg als -0.75 und nicht näher als 0.75 sind.
        // Berechne, wo sich der Mittelpunkt in diesem Interval befindet.
        // weiter weg = mehr Transparenz = heller
        double nearestY = 3;
        double furthestY = -3;
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
    private static void drawCube(double[][] vertices) {
        drawShadedLine(vertices[0], vertices[1]);
        drawShadedLine(vertices[0], vertices[3]);
        drawShadedLine(vertices[1], vertices[2]);
        drawShadedLine(vertices[2], vertices[3]);
        drawShadedLine(vertices[4], vertices[5]);
        drawShadedLine(vertices[4], vertices[7]);
        drawShadedLine(vertices[5], vertices[6]);
        drawShadedLine(vertices[6], vertices[7]);
        drawShadedLine(vertices[8], vertices[9]);
        drawShadedLine(vertices[8], vertices[11]);
        drawShadedLine(vertices[9], vertices[10]);
        drawShadedLine(vertices[10], vertices[11]);
        drawShadedLine(vertices[0], vertices[6]);
        drawShadedLine(vertices[1], vertices[7]);
        drawShadedLine(vertices[2], vertices[4]);
        drawShadedLine(vertices[3], vertices[5]);
        drawShadedLine(vertices[0], vertices[10]);
        drawShadedLine(vertices[1], vertices[11]);
        drawShadedLine(vertices[2], vertices[8]);
        drawShadedLine(vertices[3], vertices[9]);
    }

    /**
     * Rotiert das gegebene Objekt um die x-Achse und anschließend um die y-Achse.
     * Gibt dann die neuen Eckpunkte des rotierten Objekts zurück.
     */
    private static double[][] rotateObject(double[][] vertices, double angleX, double angleY) {
        double[][] xRotated = matrixMultiplication(vertices, rotationXMatrix(angleX));
      //  double[][] xyRotated = matrixMultiplication(xRotated, rotationYMatrix(angleY));
        return xRotated;
    }

    public static void main(String[] args) {
        StdDraw.setScale(-1.5, 1.5);

        double[][] cube = Points();

        // unendliche Animationsschleife
        for(double angle = 0; ; angle += 0.01) {
            double[][] cubeRotated = rotateObject(cube, angle, angle);
            drawCube(cubeRotated);

            // Bild für 100 Millisekunden anzeigen, dann nächstes Bild berechnen
            StdDraw.show(10);
            StdDraw.clear();
        }
    }
}
