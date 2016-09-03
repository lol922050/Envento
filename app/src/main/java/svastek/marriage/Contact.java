package svastek.marriage;

/**
 * Created by Tatson on 07-12-2015.
 */
public class Contact {

    //private variables
    int _id;
    private String thumbnailUrl;
    String _name;
    String _phone_number;
    String qty;

    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(int id, String name, String _phone_number,String qty){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this.qty = qty;
    }

    // constructor
    public Contact(String name, String _phone_number, String qty){
        this._name = name;
        this._phone_number = _phone_number;
        this.qty = qty;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting phone number
    public String getPhoneNumber(){
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }


    public String getqty(){
        return this.qty;
    }

    // setting name
    public void setqty(String qty){
        this.qty=qty;
    }


}
