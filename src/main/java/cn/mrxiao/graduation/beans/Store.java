package cn.mrxiao.graduation.beans;

/**
 * @author mrxiao
 * @date 2020/4/16 - 22:05
 **/
public class Store {
    private Integer id;
    private String email;
    private String phoneNumber;
    private String storeName;
    private Integer capacity;
    private Integer equipmentEatTime;
    private Integer state;
    private String storePicture;
    private String licensePicture;
    private String identityPicture;
    private String wechatPicture;
    private String zhifubaoPicture;
    private String city;
    private String positionLat;
    private String positionLng;
    private String address;
    private String notice;
    private String license;
    private Integer monthSales;
    private Float score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getEquipmentEatTime() {
        return equipmentEatTime;
    }

    public void setEquipmentEatTime(Integer equipmentEatTime) {
        this.equipmentEatTime = equipmentEatTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStorePicture() {
        return storePicture;
    }

    public void setStorePicture(String storePicture) {
        this.storePicture = storePicture;
    }

    public String getLicensePicture() {
        return licensePicture;
    }

    public void setLicensePicture(String licensePicture) {
        this.licensePicture = licensePicture;
    }

    public String getIdentityPicture() {
        return identityPicture;
    }

    public void setIdentityPicture(String identityPicture) {
        this.identityPicture = identityPicture;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPositionLat() {
        return positionLat;
    }

    public void setPositionLat(String positionLat) {
        this.positionLat = positionLat;
    }

    public String getPositionLng() {
        return positionLng;
    }

    public void setPositionLng(String positionLng) {
        this.positionLng = positionLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
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

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", storeName='" + storeName + '\'' +
                ", capacity=" + capacity +
                ", equipmentEatTime=" + equipmentEatTime +
                ", state=" + state +
                ", storePicture='" + storePicture + '\'' +
                ", licensePicture='" + licensePicture + '\'' +
                ", identityPicture='" + identityPicture + '\'' +
                ", wechatPicture='" + wechatPicture + '\'' +
                ", zhifubaoPicture='" + zhifubaoPicture + '\'' +
                ", city='" + city + '\'' +
                ", positionLat='" + positionLat + '\'' +
                ", positionLng='" + positionLng + '\'' +
                ", address='" + address + '\'' +
                ", notice='" + notice + '\'' +
                ", license='" + license + '\'' +
                ", monthSales=" + monthSales +
                ", score=" + score +
                '}';
    }
}
