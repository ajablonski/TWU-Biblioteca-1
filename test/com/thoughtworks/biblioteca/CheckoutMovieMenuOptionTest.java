package com.thoughtworks.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CheckoutMovieMenuOptionTest {

    private CheckoutMovieMenuOption option;
    private PrintStream out;
    private BufferedReader in;
    private List<Movie> movies;

    @Before
    public void setUp(){
        out = mock(PrintStream.class);
        in = mock(BufferedReader.class);
        movies = new ArrayList<Movie>();
        movies.add(mock(Movie.class));
        option = new CheckoutMovieMenuOption(movies, out, in);
    }

    @Test
    public void shouldDisplayMessageOnSuccessfulCheckout() throws IOException {
        when(in.readLine()).thenReturn("1");

        option.execute();

        verify(out).println("Thank you! Enjoy the movie.");
    }

    @Test
    public void shouldDisplayErrorMessageOnUnsuccessfulCheckout() throws IOException {
        when(in.readLine()).thenReturn("2");

        option.execute();

        verify(out).println("That movie is not available");
    }

    @Test
    public void shouldCheckoutMovieOnSuccessfulCheckout() throws IOException {
        when(in.readLine()).thenReturn("1");
        when(movies.get(0).isCheckedOut()).thenReturn(false);
        option.execute();
        verify(movies.get(0)).checkOut();
    }

    @Test
    public void shouldNotCheckoutMovieIfAlreadyCheckedOut() throws IOException {
        when(in.readLine()).thenReturn("1");
        when(movies.get(0).isCheckedOut()).thenReturn(true);
        option.execute();
        verify(movies.get(0), never()).checkOut();
    }

    @Test
    public void shouldDisplayMovieListOnExecute() throws IOException {
        when(in.readLine()).thenReturn("1");
        when(movies.get(0).getDetails()).thenReturn("TestDetails");
        option.displayMoviesWithNumbers();

        verify(out).println("1. TestDetails");
    }

    @Test
    public void shouldPromptUserOnExecute() throws IOException {
        when(in.readLine()).thenReturn("1");
        option.execute();

        verify(out).print("Choose a movie: ");
    }

    @Test
    public void shouldRequireUserLogin() {
        assertTrue(option.needsLogin());
    }
}