package br.com.marks.brinquedoteca.a;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.marks.brinquedoteca.R;

/**
 * A fragment representing a single Principal detail screen.
 * This fragment is either contained in a {@link PrincipalListActivity}
 * in two-pane mode (on tablets) or a {@link PrincipalDetailActivity}
 * on handsets.
 */
public class PrincipalDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PrincipalDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            Activity activity = this.getActivity();
        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.navigation_view);
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(navigationView.getId()+"");
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.principal_detail, container, false);

        ((TextView) rootView.findViewById(R.id.principal_detail)).setText("DETALHES");

        return rootView;
    }
}
