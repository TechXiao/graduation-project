package cn.mrxiao.graduation.beans;

/**
 * @author mrxiao
 * @date 2020/4/27 - 16:24
 **/
public class Food {
    private Integer id;
    private String uuid;
    private String foodName;
    private String description;
    private Float price;
    private String foodGroup;
    private Integer state;
    private String pictureName;
    private Integer monthSales;
    private Float score;
    private Integer storeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(String foodGroup) {
        this.foodGroup = foodGroup;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public Integer getMonthSales() {
        return monthSales;
    }

    public void setMonthSales(Integer monthSales) {
        this.monthSales = monthSales;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", foodName='" + foodName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", foodGroup='" + foodGroup + '\'' +
                ", state=" + state +
                ", pictureName='" + pictureName + '\'' +
                ", monthSales=" + monthSales +
                ", score=" + score +
                ", storeId=" + storeId +
                '}';
    }
}
