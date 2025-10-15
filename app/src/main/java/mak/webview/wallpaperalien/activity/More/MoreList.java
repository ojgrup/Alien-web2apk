package mak.webview.wallpaperalien.activity.More;



public class MoreList {

    public String avatar_url;
    public String html_url;
    public String psl_url;
    public int viewType;
    public MoreList() {

    }


    public Integer getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getPsl_url() {
        return psl_url;
    }

    public String getHtml_url() {
        return html_url;
    }



    public MoreList(String jdl, String img, String ps) {
        this.html_url = jdl;
        this.avatar_url = img;
        this.psl_url = ps;

    }

}
