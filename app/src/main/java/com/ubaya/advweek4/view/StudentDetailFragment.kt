package com.ubaya.advweek4.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModelProvider
import com.ubaya.advweek4.R
import com.ubaya.advweek4.databinding.FragmentStudentDetailBinding
import com.ubaya.advweek4.model.Student
import com.ubaya.advweek4.util.loadImage
import com.ubaya.advweek4.viewmodel.DetailViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 * Use the [StudentDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentDetailFragment : Fragment(), ButtonUpdateClickListener, ButtonNotifClickListener {
    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentStudentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            val studentID = StudentDetailFragmentArgs.fromBundle(requireArguments()).studentID
            viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
            viewModel.fetch(studentID)

            observeViewModel()

            dataBinding.updateListener = this
            dataBinding.notifListener = this
        }
    }

    private fun observeViewModel() {
        viewModel.studentLiveData.observe(viewLifecycleOwner) {
            it?.let {
                dataBinding.studentDetail = it
            }

//            student?.let {
//                editID.setText(it.id)
//                editName.setText(it.name)
//                editDOB.setText(it.dob)
//                editPhone.setText(it.phone)
//                imageDetailStudentPhoto.loadImage(it.photoURL, progressLoadingStudentPhotoDetail)
//                buttonNotif.setOnClickListener {
//                    Observable.timer(5, TimeUnit.SECONDS)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe {
//                            Log.d("mynotif", "Notification delayed after 5 seconds")
//                            student.name?.let { it1 -> MainActivity.showNotification(it1,
//                                "Notification created", R.drawable.ic_baseline_person_24) }
//                        }
//                }
//            }
        }
    }

    override fun onButtonUpdateClick(v: View, student:Student) {
        Log.d("updateClick", "Updated!")
    }

    override fun onButtonShowNotifClick(v: View) {
        Observable.timer(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("mynotif", "Notification delayed after 5 seconds")
                MainActivity.showNotification(v.tag.toString(),
                    "Notification created", R.drawable.ic_baseline_person_24)
            }
    }
}