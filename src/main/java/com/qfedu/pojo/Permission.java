package com.qfedu.pojo;

public class Permission {
    private Integer id;

    private String pername;

    private String perdesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPername() {
        return pername;
    }

    public void setPername(String pername) {
        this.pername = pername == null ? null : pername.trim();
    }

    public String getPerdesc() {
        return perdesc;
    }

    public void setPerdesc(String perdesc) {
        this.perdesc = perdesc == null ? null : perdesc.trim();
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", pername='" + pername + '\'' +
                ", perdesc='" + perdesc + '\'' +
                '}';
    }
}