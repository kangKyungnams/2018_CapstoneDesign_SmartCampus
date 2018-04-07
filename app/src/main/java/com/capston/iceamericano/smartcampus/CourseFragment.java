package com.capston.iceamericano.smartcampus;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CourseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment
{
    View v;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance(String param1, String param2) {
        CourseFragment fragment = new CourseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private ArrayAdapter yearAdapter;
    private Spinner yearSpinner;
    private ArrayAdapter semesterAdapter;
    private Spinner semesterSpinner;
    private ArrayAdapter areaAdapter;
    private Spinner areaSpinner;

    private String courseYear = "";
    private String courseSemester = "";
    private String courseMajor = "";

    private ListView courseListView;
    private CourseListAdapter adapter;
    private List<Course> courseList;

    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);

//        final RadioGroup courseUniversityGroup = (RadioGroup)getView().findViewById(R.id.)
        yearSpinner = (Spinner)getView().findViewById(R.id.yearSpinner);
        semesterSpinner = (Spinner)getView().findViewById(R.id.semesterSpinner);
        areaSpinner = (Spinner)getView().findViewById(R.id.areaSpinner);

        yearAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.year, android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        semesterAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.semester, android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setAdapter(semesterAdapter);
        areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.area, android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);

        courseListView = (ListView)getView().findViewById(R.id.courseListView);
        courseList = new ArrayList<Course>();
        adapter = new CourseListAdapter(getContext().getApplicationContext(), courseList);
        courseListView.setAdapter(adapter);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_course, container, false);
        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onPostExecute(String result){
        try{
            courseList.clear();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            int courseID; //강의 고유 번호
            int courseYear; // 강의 년도
            String courseSemester; // 강의 학기
            String courseMajor; // 강의 해당 학과
            String courseGrade; // 강의 해당 학년
            String courseTitle; // 강의 제목
            String courseProfessor; // 강의 교수
            String courseTime; // 강의 시간대
            String courseRoom; // 강의실번호
            int courseCredit; // 강의 학점
            int courseDivide; // 강의 분반
            int coursePersonnel; //강의 해당 인원
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                courseID = object.getInt("courseID");
                courseYear = object.getInt("courseYear");
                courseSemester = object.getString("courseSemester");
                courseMajor = object.getString("courseMajor");
                courseGrade = object.getString("courseGrade");
                courseTitle = object.getString("courseTitle");
                courseTime = object.getString("courseTime");
                courseRoom = object.getString("courseRoom");
                courseProfessor = object.getString("courseProfessor");
                courseCredit = object.getInt("courseCredit");
                courseDivide = object.getInt("courseDivide");
                coursePersonnel = object.getInt("coursePersonnel");
                Course course = new Course(courseID, courseYear, courseSemester, courseMajor, courseGrade, courseTitle, courseProfessor, courseTime, courseRoom, courseCredit, courseDivide, coursePersonnel);
                courseList.add(course);
                count++;
            }
            if(count == 0){
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(CourseFragment.this.getActivity());
                dialog = builder.setMessage("조회된 강의가 없습니다.")
                        .setPositiveButton("확인",null)
                        .create();
                dialog.show();
            }
            adapter.notifyDataSetChanged();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
