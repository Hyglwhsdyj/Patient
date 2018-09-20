package com.ais.patient.been;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/8/3 0003.
 */

public class WetChat {
        /**
         * codeUrl : weixin://wxpay/bizpayurl?pr=c9mvTCl
         * package : Sign=WXPay
         * appid : wxf980f7a002905929
         * sign : 0E0B3FCE702A8BA704F8AC55F9108467
         * partnerid : 1501926651
         * prepayid : wx25151147315278e0968a1a744093447995
         * noncestr : 49767253844938393035041106083062
         * timestamp : 1527232303
         */

        @SerializedName("package")
        private String packageX;
        private String appid;
        private String sign;
        private String partnerid;
        private String prepayid;
        private String noncestr;
        private String timestamp;
        private String recordId;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

}
