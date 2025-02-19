package com.shruti.todoroomdatabase

import android.app.ActionBar
import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowId
import androidx.recyclerview.widget.LinearLayoutManager
import com.shruti.todoroomdatabase.databinding.CustomDialogSubtaskBinding
import com.shruti.todoroomdatabase.databinding.CustomDialogTodoBinding
import com.shruti.todoroomdatabase.databinding.FragmentMainBinding
import com.shruti.todoroomdatabase.databinding.FragmentSubTaskBinding
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SubTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubTaskFragment : Fragment(), SubTaskAdapter.subInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding : FragmentSubTaskBinding
    lateinit var mainActivity: MainActivity
    lateinit var todoDatabase: TodoDatabase
    var item = ArrayList<SubTaskEntity>()
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter : SubTaskAdapter
    var stringDate = ""
    var todoId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            todoId = it.getString("todoId", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SubTaskAdapter(item,this)
        binding.recyler.adapter = adapter
        linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        binding.recyler.layoutManager = linearLayoutManager
        todoDatabase = TodoDatabase.getDatabase(requireContext())
        getSub()
        binding.fab.setOnClickListener {
            val dialog = Dialog(requireContext())
            val dialogBinding = CustomDialogSubtaskBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
            dialogBinding.time.setOnClickListener {
                var dialogTime = DatePickerDialog(mainActivity)
                dialogTime.setOnDateSetListener{view,year,month,dayOfMonth ->
                    var simpleDateFormat = SimpleDateFormat("dd/MMM/yy")
                    var calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR,year)
                    calendar.set(Calendar.MONTH,month)
                    calendar.set(Calendar.DATE,dayOfMonth)
                    stringDate = simpleDateFormat.format(calendar.time)
                    dialogBinding.time.setText(stringDate)
                }
                dialogTime.show()
            }
            dialogBinding.btnCreate.setOnClickListener {
                if (dialogBinding.etTodo.text.isNullOrEmpty()) {
                    dialogBinding.etTodo.error = "Enter Todo name"
                } else {
                    todoDatabase.todoDao().insertSub(SubTaskEntity(subTaskName = dialogBinding.etTodo.text.toString(), todoId = todoId.toInt(), date = stringDate))
                    getSub()
                    dialog.dismiss()
                }
            }
            adapter.notifyDataSetChanged()
            dialog.show()
        }
    }
    fun getSub(){
        item.clear()
        item.addAll(todoDatabase.todoDao().getSub(todoId.toInt()))
        adapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SubTaskFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SubTaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun show(subTaskEntity: SubTaskEntity, position: Int) {
        val dialog = Dialog(requireContext())
        val dialogBinding = CustomDialogSubtaskBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialogBinding.etTodo.setText(item[position].subTaskName)
        dialogBinding.time.setText(item[position].date)
        dialogBinding.btnCreate.setText("Update")
        dialogBinding.time.setOnClickListener {
            var dialog = DatePickerDialog(mainActivity)
            dialog.setOnDateSetListener{view,year,month,dayOfMonth ->
                var simpleDateFormat = SimpleDateFormat("dd/MMM/yy")
                var calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR,year)
                calendar.set(Calendar.MONTH,month)
                calendar.set(Calendar.DATE,dayOfMonth)
                stringDate = simpleDateFormat.format(calendar.time)
                dialogBinding.time.setText(stringDate)
            }
            dialog.show()
        }
        dialogBinding.btnCreate.setOnClickListener {
            if (dialogBinding.etTodo.text.isNullOrEmpty()) {
                dialogBinding.etTodo.error = "Enter Todo name"
            } else {
                todoDatabase.todoDao().updateSub(SubTaskEntity(subTaskId = item[position].subTaskId, subTaskName = dialogBinding.etTodo.text.toString(), todoId = todoId.toInt(), date = stringDate))
                getSub()
                dialog.dismiss()
            }
        }
        adapter.notifyDataSetChanged()
        dialog.show()
    }

    override fun delete(subTaskEntity: SubTaskEntity, position: Int) {
       todoDatabase.todoDao().deleteSub(item[position])
        getSub()
        adapter.notifyDataSetChanged()
    }

    override fun todoMark(subTaskEntity: SubTaskEntity, position: Int) {
        todoDatabase.todoDao().updateSub(SubTaskEntity(subTaskId = item[position].subTaskId, todoId = todoId.toInt(), subTaskName = item[position].subTaskName, completed = item[position].completed))
        adapter.notifyDataSetChanged()
    }
}