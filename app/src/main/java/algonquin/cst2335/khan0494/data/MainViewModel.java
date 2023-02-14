package algonquin.cst2335.khan0494.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public String editString;
    public MutableLiveData<Boolean>  isSelected = new MutableLiveData<>();
}