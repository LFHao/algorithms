package airbnb;

import java.util.ArrayList;
import java.util.List;

/**
 * 找到范围最广的IP/CIDR，但是范围太大了需要进一步缩小。缩小的方法是把最大的CIDR对半分得到两个CIDR。然后可以把输入的IP地址范围分到
 * 这两个CIDR中去，然后再递归求解，直到CIDR恰好覆盖IP区间则得到合格的CIDR。
 */
public class BN10IPRangeToCIDR {
    public List<String> ipRange2Cidr(String startIp, int range) {
        List<String> res = new ArrayList<>();
        int start = ipToLong(startIp);
        int end = start + range - 1;

        // search until start == end
        while (start <= end) {
            // identify the location of first 1's from lower bit to higher
            // bit of start IP  e.g. 00000001.00000001.00000001.01101100, return 4 (100)
            // -start is to flip all the bits of start and plus 1
            long locOfFirstOne = start & -start;
            int curMask = 32 - (int) (Math.log(locOfFirstOne) / Math.log(2));

            // calculate how many IP addresses between the start and end
            // e.g. between 1.1.1.111 and 1.1.1.120, there are 10 IP address
            // 3 bits to represent 8 IPs, from 1.1.1.112 to 1.1.1.119 (119 -
            // 112 + 1 = 8)
            double curRange = Math.log(end - start + 1) / Math.log(2);
            int curRangeMask = 32 - (int) Math.floor(curRange);

            // if the currRangeMask is larger than curMask
            // which means the numbers of IPs from start to end is smaller than mask range
            // so we can't use as many as bits we want to mask the start IP to avoid exceed the end IP
            // Otherwise, if currRangeMask is smaller than curMask, which
            // means number of IPs is larger than mask range
            // in this case we can use curMask to mask as many as IPs from start we want.
            curMask = Math.max(curMask, curRangeMask);

            String longToIp = longToIP(start);
            res.add(longToIp + "/" + curMask);

            start += Math.pow(2, 32 - curMask);
        }

        return res;
    }

    private int ipToLong(String startIp) {
        int[] ip = new int[4];
        // HOW Escape work
        String[] ipString = startIp.split("\\.");
        for (int i = 0; i < ip.length; i++) {
            ip[i] = Integer.valueOf(ipString[i]);
        }

        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    private String longToIP(int ipLong) {
        StringBuilder sb = new StringBuilder();
        // 32~24 bit. >>> means unsigned right shift. >> means signed right shift
        sb.append(ipLong >>> 24);
        sb.append(".");
        // & (24 bits 1) to remove the first 32~24 bits and right shift 16 bits to get the 24~16 bits
        sb.append((ipLong & 0x00FFFFFF) >>> 16);
        sb.append(".");
        // the same to get 16~8 bits
        sb.append((ipLong & 0x0000FFFF) >>> 8);
        sb.append(".");
        sb.append(ipLong & 0x000000FF);
        return sb.toString();
    }

    public static void main(String[] args) {
        // [255.0.0.7/32, 255.0.0.8/29, 255.0.0.16/32]
        //[1.1.1.0/30]
        //[1.1.1.1/32, 1.1.1.2/31, 1.1.1.4/32]
        BN10IPRangeToCIDR sol = new BN10IPRangeToCIDR();
        List<String> res = sol.ipRange2Cidr("255.255.255.255", 2);
        // "255.0.0.7/32", "255.0.0.8/29", "255.0.0.16/32"
        System.out.println(res);

        res = sol.ipRange2Cidr("1.1.1.0", 4);
        // "1.1.1.0/30"
        System.out.println(res);

        res = sol.ipRange2Cidr("1.1.1.1", 4);
        // "1.1.1.1/32", "1.1.1.2/31", "1.1.1.4/32"
        System.out.println(res);

    }

}
