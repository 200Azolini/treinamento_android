package com.example.administrador.myapplication.model.entities;

import com.example.administrador.myapplication.model.persistence.MemoryClientRepository;

import java.util.List;

public class Client {

    private String name;
    private Integer age;
    private String address;
    private String phoneNumber;
    private MemoryClientRepository memoryClientRepository;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
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
        MemoryClientRepository.getInstance().save(this);
    }

    public static List<Client> getAll(){
        return MemoryClientRepository.getInstance().getAll();
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
}