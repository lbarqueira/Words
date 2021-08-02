package com.example.wordsapp


import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.FragmentLetterListBinding


class LetterListFragment : Fragment() {
    // The type should be FragmentLetterListBinding? and it should have an initial value of null.
    private var _binding: FragmentLetterListBinding? = null

    // Here, get() means this property is "get-only".
    // That means you can get the value, but once assigned (as it is here), you can't assign it to something else.
    private val binding get() = _binding!!

    // Below the binding property, create a property for the recycler view.
    private lateinit var recyclerView: RecyclerView

    // Keeps track of which LayoutManager is in use for the [RecyclerView]
    private var isLinearLayoutManager = true

    // To display the options menu, override onCreate().
    // Inside onCreate() call setHasOptionsMenu() passing in true.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    // Remember that with fragments, the layout is inflated in onCreateView().
    // Implement onCreateView() by inflating the view, setting the value of _binding,
    // and returning the root view.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLetterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    // set the value of the recyclerView property in onViewCreated(), and call chooseLayout()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // the binding class already created a property for recyclerView,
        // and you don't need to call findViewById() for each view.
        recyclerView = binding.recyclerView
        chooseLayout()
    }

    // in onDestroyView(), reset the _binding property to null, as the view no longer exists
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)

        val layoutButton = menu.findItem(R.id.action_switch_layout)
        setIcon(layoutButton)
    }

    /**
     * Sets the LayoutManager for the [RecyclerView] based on the desired orientation of the list.
     */
    // The only other difference to note is that because unlike an activity, a fragment is not a Context.
    // You can't pass in this (referring to the fragment object) as the layout manager's context.
    // However, fragments provide a context property you can use instead.
    private fun chooseLayout() {
        if (isLinearLayoutManager) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            recyclerView.layoutManager = GridLayoutManager(context,4)
        }
        recyclerView.adapter = LetterAdapter()
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return

        // Set the drawable for the menu icon based on which LayoutManager is currently in use

        // An if-clause can be used on the right side of an assignment if all paths return a value.
        // The following code is equivalent to
        // if (isLinearLayoutManager)
        //     menu.icon = ContextCompat.getDrawable(this, R.drawable.ic_linear_layout)
        // else menu.icon = ContextCompat.getDrawable(this, R.drawable.ic_grid_layout)
        menuItem.icon =
            if (isLinearLayoutManager)
                ContextCompat.getDrawable(this.requireContext(),R.drawable.ic_linear_layout)
            else ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_grid_layout)
    }

    /**
     * Determines how to handle interactions with the selected [MenuItem]
     * Where you'll actually call chooseLayout() when the button is selected.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                // Sets isLinearLayoutManager (a Boolean) to the opposite value
                isLinearLayoutManager = !isLinearLayoutManager
                // Sets layout and icon
                chooseLayout()
                setIcon(item)

                return true
            }
            //  Otherwise, do nothing and use the core event handling

            // when clauses require that all possible paths be accounted for explicitly,
            //  for instance both the true and false cases if the value is a Boolean,
            //  or an else to catch all unhandled cases.
            else -> super.onOptionsItemSelected(item)
        }
    }


}