package activity.xbl.com.volleydemo;

import java.util.List;

/**
 * Created by April on 2017/4/8.
 * Json数据解析成的类:使用了GsonFormat
 *
 */

public class Data  {

    /**
     * count : 0
     * datas : [{"city_id":7,"city_name":"洛阳"}]
     * page : 0
     * state : 1
     */

    private int count;
    private int page;
    private int state;
    private List<DatasBean> datas;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * city_id : 7
         * city_name : 洛阳
         */

        private int city_id;
        private String city_name;

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }
    }
}
