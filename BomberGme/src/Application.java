import java.util.Random;
import java.util.Scanner;

public class Application {

    public static Scanner scanner = new Scanner(System.in);
    public static Random random = new Random(4);


    public static final int BATTLE_FIELD_ROW_COUNT = 15; //брой на редове
    public static final int BATTLE_FIELD_COL_COUNT = 15; //брой на колони

    public static final int TERRAIN = 0;  // бойния терен
    public static final int SMALL_TARGET = 1;
    public static final int MEDIUM_TARGET = 2;
    public static final int BIG_TARGET = 3;

    public static final int ENEMY_BAY_PETKAN = 4;
    public static final int BOMB = 5;
    public static int lives = 4;

    public static int TANKER = 11; // танкерът
    public static int SNIPER_MAN = 12; //снайпериста
    public static  int THE_SPY = 13; // шпионинът
    public static  int FISHER_MAN = 14; // рибарят

    public static final String SWITCH_PLAYER = "c";
    public static final String PLACING_BOMB_IN_THE_BATTLE_STATION = "f";


    static int[][] unitCollection = { //КОЛЕКЦИЯ ОТ БОЙНАТА БРИГАДА
            {14, 11, TANKER}, // 1
            {14, 12, SNIPER_MAN}, // 2
            {14, 13, THE_SPY}, // 3
            {14, 14, FISHER_MAN}  // 4
    };

    static int[][] enemy = {  // БАЙ ПЕТКАН

            {1, 4, ENEMY_BAY_PETKAN}
    };

    static int[][] smallBuilding = {   //ПОЗИЦИОНИРАНЕ НА МАЛКАТА СГРАДА С КООРДИНАТИТЕ МУ
            {2, 2, SMALL_TARGET},{2, 3, SMALL_TARGET},
            {3, 2, SMALL_TARGET}, {3, 3, SMALL_TARGET}
    };

    static int[][] largeBuilding = { //ПОЗИЦИОНИРАНЕ НА Голямата СГРАДА С КООРДИНАТИТЕ МУ
            {8, 1, BIG_TARGET}, {8, 2, BIG_TARGET}, {8, 3, BIG_TARGET},
            {9, 1, BIG_TARGET}, {9, 2, BIG_TARGET}, {9, 3, BIG_TARGET},
            {10, 1, BIG_TARGET}, {10, 2, BIG_TARGET}, {10, 3, BIG_TARGET}
    };

    static int[][] mediumBuilding = { //ПОЗИЦИОНИРАНЕ НА СРЕДНАТА СГРАДА С КООРДИНАТИТЕ МУ
            {4, 7, MEDIUM_TARGET}, {4, 8, MEDIUM_TARGET}, {4, 9, MEDIUM_TARGET},
            {5, 7, MEDIUM_TARGET},  {5, 8, MEDIUM_TARGET}, {5, 9, MEDIUM_TARGET}
    };

    static int[][][] buildingCollection = { // КОЛЕКЦИЯ ОТ ВСИЧКИ СГРАДИ
            smallBuilding,mediumBuilding,largeBuilding,enemy
    };

    public static boolean isUnitAvailable(int unit){
        for (int i = 0; i < unitCollection.length; i++){
            if (unitCollection[i][2] == unit) return true;

        }
        return false;
    }

    public static boolean isTankAvailable(){
        return isUnitAvailable(TANKER);

    }

    public static boolean isSpyAvailable(){
        return isUnitAvailable(THE_SPY);

    }
    public static boolean isSniperAvailable(){
        return isUnitAvailable(SNIPER_MAN);
    }
    public static boolean isDemoAvailable(){
        return isUnitAvailable(FISHER_MAN);
    }

    public static void main(String[] args) {

        int[][] collection;

        while (true){

            collection = bootstrap();
            render(collection);

            Scanner scanner = new Scanner(System.in);
            String direction = scanner.nextLine();
            Random random = new Random();
            int direction1 = random.nextInt(4 + 1);

            System.out.println("Choose direction: ");
            move(direction);
            moveEnemySimulation(direction1);

            if (direction.equals(SWITCH_PLAYER)){
                playerMenu(direction);
            }
            if (direction.equals(PLACING_BOMB_IN_THE_BATTLE_STATION)){
                buildingCollection.equals("@");
                render(collection);
            }
            enemyShot();
            if (isLifeLost()){
                lives--;
                System.out.println(lives + " lives left ");
            }
            if (lives == 0){
                System.out.println("Бойната брикада е унищожена");
                Color.green2(" Game over ");
                System.out.println("End of a transmission");
                break;
            }

        }
    }

    public static boolean isMovePossible(int row, int col){ // Проверка за движение

        if (isSpyAvailable()) return true;
        if (isSniperAvailable()) return true;
        if (isDemoAvailable()) return true;
        if (isTankAvailable()) return true;

        if (row >= BATTLE_FIELD_ROW_COUNT) return  false;
        if (col >= BATTLE_FIELD_COL_COUNT) return  false;

        return true;
    }
    public static void playerMenu(String direction){
        System.out.println("1. Тракторист хулиган");
        System.out.println("2. Хвърляч на камъни ");
        System.out.println("3. Пиянде");
        System.out.println("4. Рибар");

        String changeLeader = scanner.nextLine();

        if (changeLeader.equals("2")){

            int src = 2;
            int dest = 1;

            int temp = unitCollection[0][src];
            unitCollection[0][src] = unitCollection[2][dest];
            unitCollection[2][dest] = temp;
            SNIPER_MAN = temp;
            for (int i = 1; i< unitCollection.length; i++){
                    System.out.println(unitCollection[i][src]);
                    System.out.println();

            }
        }


    }

    public static void move(String direction) {

        //copying original collection which contains the positions of specialists
        int[][] originalUnitCollection = Collection.copy(unitCollection);

        //row and col
        int destinationRow = unitCollection[0][0] + getRowCoefficient(direction);
        int destinationCol = unitCollection[0][1] + getColCoefficient(direction);


        if (isMovePossible(destinationRow, destinationCol)){

            destinationRow = transformRow(destinationRow);
            destinationCol = transformCol(destinationCol);


            //determine coordinates of 1st element
            // moving UP col - 1; moving DOWN col + 1
            //moving LEFT row - 1; moving RIGHT row + 1;

            unitCollection[0][0] = destinationRow;
            unitCollection[0][1] = destinationCol;
            //rest of the coordinates
            for (int i = 1; i < unitCollection.length; i++) {
                unitCollection[i][0] = originalUnitCollection[i - 1][0];
                unitCollection[i][1] = originalUnitCollection[i - 1][1];

                if (direction.equals("f") && destinationRow== 5 && destinationCol == 5){
                    buildingCollection.equals("@");
                    //System.out.println("@");
                }
            }
        }
    }

    public static int transformRow(int row){
        if (row >= BATTLE_FIELD_ROW_COUNT) return 0;
        if (row < 0) return BATTLE_FIELD_ROW_COUNT - 1;
        return row;
    }

    public static int transformCol(int col){
        if (col >= BATTLE_FIELD_COL_COUNT) return 0;
        if (col < 0) return BATTLE_FIELD_COL_COUNT - 1;
        return col;
    }

    public static int getRowCoefficient(String direction) { //координати за движение

        if (direction.equals("w")) return -1;
        if (direction.equals("s")) return +1;

        return 0;
    }
    public static int getColCoefficient(String direction) {


        if (direction.equals("a")) return -1;
        if (direction.equals("d")) return +1;
        return 0;
    }

    public static boolean isLifeLost(){
        if (enemy[0][0] == unitCollection[0][0] && enemy[0][1] == unitCollection[0][1]){
            return true;
        }
        return false;
    }

    public static void enemyShot(){   //метод за стрелба от страната на бай Петкан
        Random random = new Random();
        int number = random.nextInt(24);
        int lives = 4;


            if (isLifeLost() && number % 11 == 0) {
                lives--;
                System.out.println("you lose a life " + lives + " left");
            }
    }

    public static void moveEnemySimulation(int direction1) { // Придвижване на бай Петкан на случаен принцип с рандом генерацията

        //copying original collection which contains the positions of specialists
        int[][] originalUnitCollection = Collection.copy(unitCollection);

        //row and col
        int destinationRowEnemy = enemy[0][0] + getRowCoefficientEnemy(direction1);
        int destinationColEnemy = enemy[0][1] + getColCoefficientEnemy(direction1);

        if (isMovePossible(destinationRowEnemy, destinationColEnemy)){

            destinationRowEnemy = transformRowEnemy(destinationRowEnemy);
            destinationColEnemy = transformColEnemy(destinationColEnemy);


            //determine coordinates of 1st element
            // moving UP col - 1; moving DOWN col + 1
            //moving LEFT row - 1; moving RIGHT row + 1;

            enemy[0][0] = destinationRowEnemy;
            enemy[0][1] = destinationColEnemy;
            //rest of the coordinates
            for (int i = 1; i < enemy.length; i++) {
                enemy[i][0] = originalUnitCollection[i - 1][0];
                enemy[i][1] = originalUnitCollection[i - 1][1];
            }
        }
    }

    public static int transformRowEnemy(int row){
        if (row >= BATTLE_FIELD_ROW_COUNT) return 0;
        if (row < 0) return BATTLE_FIELD_ROW_COUNT - 1;
        return row;
    }

    public static int transformColEnemy(int col){
        if (col >= BATTLE_FIELD_COL_COUNT) return 0;
        if (col < 0) return BATTLE_FIELD_COL_COUNT - 1;
        return col;
    }

    public static int getRowCoefficientEnemy(int direction1) { //координати за движение

        Random random = new Random();
        direction1 = random.nextInt(4);
        if (direction1 == 1 ) return -1;
        if (direction1 == 2) return +1;

        return 0;
    }
    public static int getColCoefficientEnemy(int direction1) {

        direction1 = random.nextInt(4 + 1);
        if (direction1 == 3 ) return -1;
        if (direction1 == 4) return +1;
        return 0;
    }




    public static int [][] bootstrap() {
        int[][] collection = new int[15][15];

        //BOOTSTRAP BUILDING
        for (int buildingCountIndex = 0; buildingCountIndex < buildingCollection.length; buildingCountIndex++) {
            int[][] buildingElement = buildingCollection[buildingCountIndex];
            for (int i = 0; i < buildingElement.length; i++) {

                int row = buildingElement[i][0];
                int col = buildingElement[i][1];
                int sign = buildingElement[i][2];
                collection[row][col] = sign;

            }
        }

        //BOOTSTRAP unit / battle brigade
        for (int i = 0; i < unitCollection.length; i++){
            int row = unitCollection[i][0];
            int col = unitCollection[i][1];
            int battleOrder = (i + 1) * 10;
            int uniSign = (unitCollection[i][2] - 10);
            int sign = battleOrder + uniSign;

            collection[row][col] = sign;
        }


        return collection;
    }

    public static void render(int[][] collection) {

        for (int row = 0; row < BATTLE_FIELD_ROW_COUNT; row++) {  // Визуализация на игралното поле
            for (int col = 0; col < BATTLE_FIELD_COL_COUNT; col++) {

                int element = collection[row][col];
                     if (collection[row][col] == BOMB){
                         Color.black("@ ");continue;
                     }

                if (element == TERRAIN) {   // компонентите със съответните цветове и символи
                    Color.yellow("X  ");continue;
                }
                if (element == SMALL_TARGET) {
                    Color.red("*  ");continue;
                }
                if (element == MEDIUM_TARGET) {
                    Color.green("%  ");continue;
                }
                if (element == BIG_TARGET) {
                    Color.blue("&  ");continue;
                }
                if (element == ENEMY_BAY_PETKAN){
                    Color.white("$ ");continue;
                }
                if (element == TANKER){
                    Color.purple("11 ");continue;
                } if (element == BOMB){
                    Color.black("@ ");continue;
                }
                    Color.cyan(element + " ");

            }
            System.out.println(" ");
        }
    }
}


