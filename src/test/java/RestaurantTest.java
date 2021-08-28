import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");
    Restaurant restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);

    @BeforeEach
    public void beforeEach(){
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //Arrange
        Restaurant spiedRestaurant = Mockito.spy( restaurant );
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(LocalTime.of( 19,0,0 ));

        //Act
        Boolean restaurantOpen;
        restaurantOpen = spiedRestaurant.isRestaurantOpen();

        //Assert
        assertTrue( restaurantOpen );
        verify( spiedRestaurant, times( 1 ) ).getCurrentTime();
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //Arrange
        Restaurant spiedRestaurant = Mockito.spy( restaurant );
        when(spiedRestaurant.getCurrentTime()).thenReturn(LocalTime.of(23, 0, 0));

        //Act
        Boolean restaurantClosed;
        restaurantClosed = !spiedRestaurant.isRestaurantOpen();

        //Assert
        assertTrue( restaurantClosed );
        verify( spiedRestaurant, times( 1 ) ).getCurrentTime();
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){



        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    @Test
    public void no_item_selected_order_value_should_be_zero(){
        //Arrange
        List<String> selectedItems = new ArrayList<String>();
        //Act
        int orderValue;
        orderValue = restaurant.getOrderValue(selectedItems);
        //Assert
        assertEquals(orderValue, 0);
    }

    @Test
    public void menu_items_selected_order_value_should_be_sum_of_items_price(){
        //Arrange
        List<String> selectedItems = new ArrayList<String>();
        selectedItems.add( "Sweet corn soup" ); //price 119
        selectedItems.add("Vegetable lasagne"); //price 269

        int total_value = 119 + 269;
        //Act
        int orderValue;
        orderValue = restaurant.getOrderValue(selectedItems);
        //Assert
        assertEquals(orderValue, total_value);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}