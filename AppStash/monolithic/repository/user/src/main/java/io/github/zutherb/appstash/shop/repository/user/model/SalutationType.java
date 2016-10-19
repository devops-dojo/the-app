package io.github.zutherb.appstash.shop.repository.user.model;

/**
 * @author zutherb
 */
public enum SalutationType {
    MISTER("Herr"),
    MISS("Frau");

    private String salutation;

    SalutationType(String salutation) {
        this.salutation = salutation;
    }

    public String getSalutation() {
        return salutation;
    }

    public static SalutationType bySalutation(String salutation){
        for (SalutationType salutationTypeType : SalutationType.values()){
            if(salutationTypeType.getSalutation().equals(salutation)){
                return salutationTypeType;
            }
        }
        return null;
    }
}
