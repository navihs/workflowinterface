package shark;

import javax.servlet.http.HttpServlet;

public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 6347513544676920008L;

    // -----------------------------------------------------------------------------
    public void init() {
        try {
            // "/" -> racine de l'application Web.
            InitComponent.init(this.getServletContext().getRealPath("/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // -----------------------------------------------------------------------------
}
