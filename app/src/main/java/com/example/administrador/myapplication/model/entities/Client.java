package com.example.administrador.myapplication.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.administrador.myapplication.model.persistence.MemoryClientRepository;
import com.example.administrador.myapplication.model.persistence.SQLiteClientRepository;

import java.io.Serializable;
import java.util.List;

public class Client implements Serializable, Parcelable{
    private Integer id;
    private String name;
    private Integer age;
    private ClientAddress address;
    private Integer phoneNumber;
    private MemoryClientRepository memoryClientRepository;

    public Client(){
        super();
    }

    public Client(Parcel in){
        super();
        readToParcel(in);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ClientAddress getAddress() {
        return address;
    }

    public void setAddress(ClientAddress address) {
        this.address = address;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (age != null ? !age.equals(client.age) : client.age != null) return false;
        if (address != null ? !address.equals(client.address) : client.address != null)
            return false;
        return !(phoneNumber != null ? !phoneNumber.equals(client.phoneNumber) : client.phoneNumber != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    public void save(){
        SQLiteClientRepository.getInstance().save(this);
    }

    public static List<Client> getAll(){
        return SQLiteClientRepository.getInstance().getAll();
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", memoryClientRepository=" + memoryClientRepository +
                '}';
    }

    public void delete() {
        SQLiteClientRepository.getInstance().delete(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id == null ? -1 : id);
        dest.writeString(name == null ? "" : name);
        dest.writeInt(age == null ? -1 : age);
        dest.writeInt(phoneNumber == null ? -1 : phoneNumber);
        dest.writeParcelable(address, flags);
    }

    public void readToParcel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        int partialAge = in.readInt();
        age = partialAge == -1 ? null : partialAge;
        phoneNumber = in.readInt();
        address = in.readParcelable(ClientAddress.class.getClassLoader());
    }

    public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>(){
        public Client createFromParcel(Parcel source){
            return new Client(source);
        }
        public Client[] newArray(int size){
            return new Client[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
