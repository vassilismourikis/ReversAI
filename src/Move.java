public class Move {
    
    private int pos;
    private int value;

    Move(){
        
    }

    Move(int pos, int value)
    {
        this.pos = pos;
        this.value = value;
    }

    Move(int pos, boolean contructor_differatiation)
    {
        this.pos = pos;
        this.value = -1;
    }

    Move(int value)
    {
        this.pos = -1;
        this.value = value;
    }

    

    int getPos()
    {
        return this.pos;
    }

    int getValue()
    {
        return this.value;
    }

    void setPos(int pos)
    {
        this.pos = pos;
    }

    void setValue(int value)
    {
        this.value = value;
    }

}
