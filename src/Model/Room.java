package Model;

public class Room implements IRoom{
    protected final String roomNumber;
    protected Double roomPrice;
    protected final RoomType roomType;
    protected Boolean isFree;

    public Room(String roomNumber , Double roomPrice , RoomType roomType) {
        super();
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
        this.isFree = false;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Double getRoomPrice() {
        return roomPrice;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public Boolean isFree() {
        return isFree;
    }

    public void setRoomPrice(Double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public void setFree(Boolean free) {
        this.isFree = free;
    }
}
