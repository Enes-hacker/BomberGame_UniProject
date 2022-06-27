
    public class Color {  //клас за цветовете

        public static void print(String message) {
            System.out.print(message);
        }

        public static void println(String message) {
            System.out.print(message);
        }

        public static String red(String message) {
            print("\u001B[41m" + message + "\u001B[0m");
            return message;
        }

        public static void green(String message) {
            print("\u001B[42m" + message + "\u001B[0m");
        }
        public static void green2(String message) {
            print("\u001B[32m" + message + "\u001B[0m");
        }

        public static void yellow(String message) {
            print("\u001B[33m" + message + "\u001B[0m");
        }

        public static void blue(String message) {
            print("\u001B[44m" + message + "\u001B[0m");
        }

        public static void purple(String message) {
            print("\u001B[45m" + message + "\u001B[0m");
        }
        public static void cyan(String message) {
            print("\u001B[46m" + message + "\u001B[0m");
        }
        public static void white(String message) {
            print("\u001B[47m" + message + "\u001B[0m");
        }
        public static void black(String message) {
            print("\u001B[40m" + message + "\u001B[0m");
        }
    }

