package interview.review;

import lombok.Getter;

public enum ReviewEnum {

    ONE(1, "齐"),TWO(2, "楚"), THREE(3, "燕"), FOUR(4, "赵"), FIVE(5, "魏"), SIX(6, "韩");

    @Getter
    int retCode;
    @Getter
    private String retMassage;

    ReviewEnum(int retCode, String retMassage) {
        this.retCode = retCode;
        this.retMassage = retMassage;
    }

    public static ReviewEnum foreach_ReviewEnum(int index){
        ReviewEnum[] enums = ReviewEnum.values();

        for (ReviewEnum anEnum : enums) {
            if(index == anEnum.getRetCode()){
                return anEnum;
            }
        }

        return null;
    }
}
