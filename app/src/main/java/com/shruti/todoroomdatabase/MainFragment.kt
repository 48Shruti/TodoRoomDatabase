package com.shruti.todoroomdatabase

import android.app.ActionBar
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shruti.todoroomdatabase.databinding.ActivityMainBinding
import com.shruti.todoroomdatabase.databinding.CustomDialogTodoBinding
import com.shruti.todoroomdatabase.databinding.FragmentMainBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() ,TodoAdapter.todoInterface{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding : FragmentMainBinding
    lateinit var mainActivity: MainActivity
    lateinit var todoDatabase: TodoDatabase
    var item = ArrayList<TodoEntity>()
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter : TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TodoAdapter(item, this)
        binding.recyler.adapter = adapter
        linearLayoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        binding.recyler.layoutManager = linearLayoutManager
        todoDatabase = TodoDatabase.getDatabase(mainActivity)
        getTodo()
        binding.fab.setOnClickListener {
            val dialog = Dialog(mainActivity)
            val dialogBinding = CustomDialogTodoBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.window?.setLayout(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            )
            dialogBinding.btnCreate.setOnClickListener {
                if (dialogBinding.etTodo.text.isNullOrEmpty()) {
                    dialogBinding.etTodo.error = "Enter Todo name"
                } else {
                    todoDatabase.todoDao()
                        .insertTodo(TodoEntity(name = dialogBinding.etTodo.text.toString()))
                    getTodo()
                    dialog.dismiss()
                }
            }
            adapter.notifyDataSetChanged()
            dialog.show()
        }
    }

    fun getTodo(){
        item.clear()
        item.addAll(todoDatabase.todoDao().getTodo())
        adapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun show(todoEntity: TodoEntity, position: Int) {
        var bundle = Bundle()
        bundle.putString("todoId" , todoEntity.id.toString())
        findNavController().navigate(R.id.subTaskFragment,bundle )
    }
}