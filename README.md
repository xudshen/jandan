# Jandan.app
## Screenshots
<img src="http://ww2.sinaimg.cn/bmiddle/8ece804cgw1f24g9qvnkgj21401z4tp1.jpg" width="200">
<img src="http://ww3.sinaimg.cn/bmiddle/8ece804cgw1f24gcdn1o1j21401z4n8u.jpg" width="200">
<img src="http://ww4.sinaimg.cn/bmiddle/8ece804cgw1f24gcqsk4uj21401z4n8x.jpg" width="200">
<img src="http://ww1.sinaimg.cn/bmiddle/8ece804cgw1f24gd3wuckj21401z4tk3.jpg" width="200">

# [DroidData](http://xudshen.info/2016/03/07/introducing-droiddata/)
## Workflow
<img src="http://ww3.sinaimg.cn/large/8ece804cgw1f24h1dafnuj20r80k2gnu.jpg" width="600">

## Model Structure
```
Model
  * must have id column

ModelObservable <- ModelDao._allchanges
  -> BaseObservable.notifyPropertyChanged 

ModelDao
  -> insert
     update
     delete
    -> notify ModelObservable/ContentProvider
     
  -> query => Model
  
ModelTrans <- Model
  => ModelObservable

ContentProvider <- ModelDao._allChanges
  -> insert
  -> update
  -> delete
    ->notify Cursor,notifyChange(@NonNull Uri uri, @Nullable ContentObserver observer)
  -> query -> ModelDao.query -> Cursor,setNotificationUri(ContentResolver cr, Uri uri) => Model
```

## RecyclerAdapter
```java
DDCursorRecyclerAdapter
+ public abstract VH createViewHolder(LayoutInflater inflater, int viewType, ViewGroup parent)
+ public abstract void bindViewHolder(VH viewHolder, Cursor cursor)
+ swapCursor(Cursor newCursor)

DDCursorLoaderRecyclerAdapter <- DDCursorRecyclerAdapter
+ DDCursorLoaderRecyclerAdapter(Context context, Uri uri, String[] projection, String selection,
                                String[] selectionArgs, String sortOrder)
+ onLoadFinished -> swapCursor

DDBindableCursorLoaderRVAdapter <- DDCursorLoaderRecyclerAdapter
+ ItemViewHolderCreator((inflater, viewType, parent) -> {}) //create viewholder
+ ItemLayoutSelector((position, cursor) -> {})//get the layout_res for each item
+ ItemViewDataBindingVariableAction((viewDataBinding, cursor) -> {}) //bind view with cursor data

+ onItemClickListener((itemView, position) -> {})
+ onItemSubViewClickListener(layoutRes, (viewholder, v, position) -> {})

DDBindableCursorLoaderRVHeaderAdapter <- DDBindableCursorLoaderRVAdapter
//add a header in adapter
+ HeaderViewHolderCreator((inflater, viewType, parent) -> {})
+ HeaderViewDataBindingVariableAction(viewDataBinding -> {})

DDBindableViewHolder implements IBindableViewHolder 
```

## TODO
- [ ] potential memory leak on ModelObservable
  
