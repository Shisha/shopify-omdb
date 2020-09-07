package ca.shivangisharma.ShopifyNominations.Views;

import ca.shivangisharma.ShopifyNominations.Entity.Search;
import ca.shivangisharma.ShopifyNominations.Services.MovieService;
import ca.shivangisharma.ShopifyNominations.Services.RestClientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@PageTitle("The Shoppies")
@Route("the-shoppies")
public class ShoppiesView extends VerticalLayout {

    private TextField movieSearchField = new TextField("Movie title");
    private Button fetchMovies;
    private Grid<Search> moviesGrid = new Grid<>(Search.class);
    private Grid<Search> removeGrid = new Grid<>(Search.class);
    private List<Search> nominatedList = new ArrayList<>();

    @Autowired
    private MovieService movieService;

    public ShoppiesView(@Autowired RestClientService service) {

        setClassName("header");
        add(new H1("The Shoppies"));

        searchFieldStyle();

        fetchMovies = new Button("Fetch all movies",
                e -> moviesGrid.setItems( service.getMovies(movieSearchField.getValue()) ));
        fetchMovies.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        configureNominationGrid();
        configureRemoveGrid();

        add(fetchMovies, moviesGrid, removeGrid);

    }

    private void searchFieldStyle() {
        setId("movie-search-box");
        movieSearchField.setSuffixComponent(new Icon(VaadinIcon.SEARCH));
        add(movieSearchField);
    }

    private void configureGridDataFields(Grid<Search> grid) {
        grid.removeAllColumns();
        grid.addColumn(Search::getTitle);
        grid.addColumn(Search::getYear);
    }

    private void configureNominationGrid() {
        configureGridDataFields(moviesGrid);
        moviesGrid.addComponentColumn( item -> {
            Button nominateButton = new Button("Nominate");
            nominateButton.addClickListener( click -> {
                nominatedList.add(item);
                removeGrid.setItems(nominatedList);
                movieService.nominate(item);
                nominateButton.setEnabled(false);
                if(nominatedList.size() == 5) displayBanner();
            });
            return nominateButton;
        });
    }

    private void configureRemoveGrid() {
        configureGridDataFields(removeGrid);
        removeGrid.addComponentColumn( item -> {
            Button removeButton = new Button("Remove");
            removeButton.addClickListener( click -> {
                nominatedList.remove(item);
                removeGrid.setItems(nominatedList);
                movieService.removeNominations(item);
                removeButton.setEnabled(false);
            });
            return removeButton;
        });
    }

    private void displayBanner() {
        remove(movieSearchField , fetchMovies, moviesGrid, removeGrid);
        add(new Paragraph("Nominations complete. Thank you."));
    }




}
