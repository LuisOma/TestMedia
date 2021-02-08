package com.example.testmedia.audio.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.testmedia.R
import com.example.testmedia.audio.ui.UIControlInterface
import com.example.testmedia.databinding.FragmentSettingsBinding


/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var mSettingsFragmentBinding: FragmentSettingsBinding
    private lateinit var mUIControlInterface: UIControlInterface

    private var mPreferencesFragment: PreferencesFragment? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mUIControlInterface = activity as UIControlInterface
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSettingsFragmentBinding = FragmentSettingsBinding.bind(view)

        mSettingsFragmentBinding.searchToolbar.setNavigationOnClickListener {
            mUIControlInterface.onCloseActivity()
        }

        mPreferencesFragment = PreferencesFragment.newInstance()
        mPreferencesFragment?.let { fm ->
            childFragmentManager.commit {
                replace(R.id.fragment_layout, fm)
            }
        }
    }

    fun onFiltersChanged(databaseSize: Int) {
        mPreferencesFragment?.updateFiltersPreferences(databaseSize)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SettingsFragment.
         */
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}
