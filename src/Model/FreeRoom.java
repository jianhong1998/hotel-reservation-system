package Model;

public class FreeRoom extends Room{
    public FreeRoom(String roomNumber , RoomType roomType) {
        super(roomNumber , 0.0 , roomType);
        this.setFree(true);
    }
}
