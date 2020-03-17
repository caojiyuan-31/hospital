package cn.ganwuwang.hospital.domain.query;

public class Sort {

    private Direction direction;
    private String property;

    public Sort(){}

    public Sort(Direction direction, String property){
        this.direction = direction;
        this.property = property;
    }

    public Sort(String direction, String property){

        if("DESC".equals(direction.toUpperCase())){

            this.direction = Direction.DESC;
        }else{
            this.direction = Direction.ASC;
        }
        this.property = property;
    }


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }



    public enum Direction {
        ASC,
        DESC;
    }

    @Override
    public String toString() {
        return "Sort{" +
                "direction=" + direction +
                ", property='" + property + '\'' +
                '}';
    }
}
