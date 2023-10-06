public enum Difficulties {
    HARD(26, 14, 78),
    MEDIUM(20, 10, 37),
    EASY(12, 6, 10),
    ;
    int length;
    int height;
    int numOfFlags;

    Difficulties(int x, int y, int numOfFlags) {
        length = x;
        height = y;
        this.numOfFlags = numOfFlags;
    }
}
