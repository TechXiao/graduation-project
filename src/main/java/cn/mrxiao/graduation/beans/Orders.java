package cn.mrxiao.graduation.beans;


/**
 * @author mrxiao
 * @date 2020/5/3 - 0:18
 **/
public class Orders {
    private Integer id;
    private String uuid;
    private String storeName;
    private String storePhoneNumber;
    private String customerPhoneNumber;
    private String wechatPicture;
    private String zhifubaoPicture;
    private String paymentCode;
    private String customerName;
    private String foodName;
    private Float unitPrice;
    private Integer quantity;
    private String time;
    private Integer state;
    private Integer storeId;
    private Integer customerId;
    private Integer grade;
    private String evaluate;

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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStorePhoneNumber() {
        return storePhoneNumber;
    }

    public void setStorePhoneNumber(String storePhoneNumber) {
        this.storePhoneNumber = storePhoneNumber;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getWechatPicture() {
        return wechatPicture;
    }

    public void setWechatPicture(String wechatPicture) {
        this.wechatPicture = wechatPicture;
    }

    public String getZhifubaoPicture() {
        return zhifubaoPicture;
    }

    public void setZhifubaoPicture(String zhifubaoPicture) {
        this.zhifubaoPicture = zhifubaoPicture;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", storeName='" + storeName + '\'' +
                ", storePhoneNumber='" + storePhoneNumber + '\'' +
                ", customerPhoneNumber='" + customerPhoneNumber + '\'' +
                ", wechatPicture='" + wechatPicture + '\'' +
                ", zhifubaoPicture='" + zhifubaoPicture + '\'' +
                ", paymentCode='" + paymentCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", foodName='" + foodName + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", time='" + time + '\'' +
                ", state=" + state +
                ", storeId=" + storeId +
                ", customerId=" + customerId +
                ", grade=" + grade +
                ", evaluate='" + evaluate + '\'' +
                '}';
    }
}
