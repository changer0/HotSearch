package android.view;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.Transformation;

import java.util.ArrayList;

/**
 * 为了暴露一些Android hide的变量给子类使用
 * 这个类没有任何作用, 因为ClassLoader会优先加载系统的类
 * 
 * 
 */
@SuppressLint("NewApi")
public abstract class ViewGroup extends View implements ViewParent, ViewManager {
	public static final int FOCUS_BEFORE_DESCENDANTS = 131072;
	public static final int FOCUS_AFTER_DESCENDANTS = 262144;
	public static final int FOCUS_BLOCK_DESCENDANTS = 393216;
	public static final int PERSISTENT_NO_CACHE = 0;
	public static final int PERSISTENT_ANIMATION_CACHE = 1;
	public static final int PERSISTENT_SCROLLING_CACHE = 2;
	public static final int PERSISTENT_ALL_CACHES = 3;
	protected static final int CLIP_TO_PADDING_MASK = 34;

	public ViewGroup(Context context) {
		super((Context) null, (AttributeSet) null, 0);
		throw new RuntimeException("Stub!");
	}

	public ViewGroup(Context context, AttributeSet attrs) {
		super((Context) null, (AttributeSet) null, 0);
		throw new RuntimeException("Stub!");
	}

	public ViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super((Context) null, (AttributeSet) null, 0);
		throw new RuntimeException("Stub!");
	}

	@ViewDebug.ExportedProperty(category = "focus", mapping = {
			@ViewDebug.IntToString(from = 131072, to = "FOCUS_BEFORE_DESCENDANTS"),
			@ViewDebug.IntToString(from = 262144, to = "FOCUS_AFTER_DESCENDANTS"),
			@ViewDebug.IntToString(from = 393216, to = "FOCUS_BLOCK_DESCENDANTS") })
	public int getDescendantFocusability() {
		throw new RuntimeException("Stub!");
	}

	public void setDescendantFocusability(int focusability) {
		throw new RuntimeException("Stub!");
	}

	public void requestChildFocus(View child, View focused) {
		throw new RuntimeException("Stub!");
	}

	public void focusableViewAvailable(View v) {
		throw new RuntimeException("Stub!");
	}

	public boolean showContextMenuForChild(View originalView) {
		throw new RuntimeException("Stub!");
	}

	public ActionMode startActionModeForChild(View originalView,
			ActionMode.Callback callback) {
		throw new RuntimeException("Stub!");
	}

	public View focusSearch(View focused, int direction) {
		throw new RuntimeException("Stub!");
	}

	public boolean requestChildRectangleOnScreen(View child, Rect rectangle,
			boolean immediate) {
		throw new RuntimeException("Stub!");
	}

	public boolean requestSendAccessibilityEvent(View child,
			AccessibilityEvent event) {
		throw new RuntimeException("Stub!");
	}

	public boolean onRequestSendAccessibilityEvent(View child,
			AccessibilityEvent event) {
		throw new RuntimeException("Stub!");
	}

	public boolean dispatchUnhandledMove(View focused, int direction) {
		throw new RuntimeException("Stub!");
	}

	public void clearChildFocus(View child) {
		throw new RuntimeException("Stub!");
	}

	public void clearFocus() {
		throw new RuntimeException("Stub!");
	}

	public View getFocusedChild() {
		throw new RuntimeException("Stub!");
	}

	public boolean hasFocus() {
		throw new RuntimeException("Stub!");
	}

	public View findFocus() {
		throw new RuntimeException("Stub!");
	}

	public boolean hasFocusable() {
		throw new RuntimeException("Stub!");
	}

	public void addFocusables(ArrayList<View> views, int direction) {
		throw new RuntimeException("Stub!");
	}

	public void addFocusables(ArrayList<View> views, int direction,
			int focusableMode) {
		throw new RuntimeException("Stub!");
	}

	public void findViewsWithText(ArrayList<View> outViews, CharSequence text,
			int flags) {
		throw new RuntimeException("Stub!");
	}

	public void dispatchWindowFocusChanged(boolean hasFocus) {
		throw new RuntimeException("Stub!");
	}

	public void addTouchables(ArrayList<View> views) {
		throw new RuntimeException("Stub!");
	}

	public void dispatchDisplayHint(int hint) {
		throw new RuntimeException("Stub!");
	}

	protected void dispatchVisibilityChanged(View changedView, int visibility) {
		throw new RuntimeException("Stub!");
	}

	public void dispatchWindowVisibilityChanged(int visibility) {
		throw new RuntimeException("Stub!");
	}

	public void dispatchConfigurationChanged(Configuration newConfig) {
		throw new RuntimeException("Stub!");
	}

	public void recomputeViewAttributes(View child) {
		throw new RuntimeException("Stub!");
	}

	public void bringChildToFront(View child) {
		throw new RuntimeException("Stub!");
	}

	public boolean dispatchDragEvent(DragEvent event) {
		throw new RuntimeException("Stub!");
	}

	public void dispatchSystemUiVisibilityChanged(int visible) {
		throw new RuntimeException("Stub!");
	}

	public boolean dispatchKeyEventPreIme(KeyEvent event) {
		throw new RuntimeException("Stub!");
	}

	public boolean dispatchKeyEvent(KeyEvent event) {
		throw new RuntimeException("Stub!");
	}

	public boolean dispatchKeyShortcutEvent(KeyEvent event) {
		throw new RuntimeException("Stub!");
	}

	public boolean dispatchTrackballEvent(MotionEvent event) {
		throw new RuntimeException("Stub!");
	}

	protected boolean dispatchHoverEvent(MotionEvent event) {
		throw new RuntimeException("Stub!");
	}

	public boolean onInterceptHoverEvent(MotionEvent event) {
		throw new RuntimeException("Stub!");
	}

	protected boolean dispatchGenericPointerEvent(MotionEvent event) {
		throw new RuntimeException("Stub!");
	}

	protected boolean dispatchGenericFocusedEvent(MotionEvent event) {
		throw new RuntimeException("Stub!");
	}

	public boolean dispatchTouchEvent(MotionEvent ev) {
		throw new RuntimeException("Stub!");
	}

	public void setMotionEventSplittingEnabled(boolean split) {
		throw new RuntimeException("Stub!");
	}

	public boolean isMotionEventSplittingEnabled() {
		throw new RuntimeException("Stub!");
	}

	public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
		throw new RuntimeException("Stub!");
	}

	public boolean onInterceptTouchEvent(MotionEvent ev) {
		throw new RuntimeException("Stub!");
	}

	public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
		throw new RuntimeException("Stub!");
	}

	protected boolean onRequestFocusInDescendants(int direction,
			Rect previouslyFocusedRect) {
		throw new RuntimeException("Stub!");
	}

	public void setPadding(int left, int top, int right, int bottom) {
		throw new RuntimeException("Stub!");
	}

	protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
		throw new RuntimeException("Stub!");
	}

	protected void dispatchFreezeSelfOnly(SparseArray<Parcelable> container) {
		throw new RuntimeException("Stub!");
	}

	protected void dispatchRestoreInstanceState(
			SparseArray<Parcelable> container) {
		throw new RuntimeException("Stub!");
	}

	protected void dispatchThawSelfOnly(SparseArray<Parcelable> container) {
		throw new RuntimeException("Stub!");
	}

	protected void setChildrenDrawingCacheEnabled(boolean enabled) {
		throw new RuntimeException("Stub!");
	}

	protected void onAnimationStart() {
		throw new RuntimeException("Stub!");
	}

	protected void onAnimationEnd() {
		throw new RuntimeException("Stub!");
	}

	protected void dispatchDraw(Canvas canvas) {
		throw new RuntimeException("Stub!");
	}

	protected int getChildDrawingOrder(int childCount, int i) {
		throw new RuntimeException("Stub!");
	}

	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		throw new RuntimeException("Stub!");
	}

	public void setClipChildren(boolean clipChildren) {
		throw new RuntimeException("Stub!");
	}

	public void setClipToPadding(boolean clipToPadding) {
		throw new RuntimeException("Stub!");
	}

	public void dispatchSetSelected(boolean selected) {
		throw new RuntimeException("Stub!");
	}

	public void dispatchSetActivated(boolean activated) {
		throw new RuntimeException("Stub!");
	}

	protected void dispatchSetPressed(boolean pressed) {
		throw new RuntimeException("Stub!");
	}

	protected void setStaticTransformationsEnabled(boolean enabled) {
		throw new RuntimeException("Stub!");
	}

	protected boolean getChildStaticTransformation(View child, Transformation t) {
		throw new RuntimeException("Stub!");
	}

	public void addView(View child) {
		throw new RuntimeException("Stub!");
	}

	public void addView(View child, int index) {
		throw new RuntimeException("Stub!");
	}

	public void addView(View child, int width, int height) {
		throw new RuntimeException("Stub!");
	}

	public void addView(View child, LayoutParams params) {
		throw new RuntimeException("Stub!");
	}

	public void addView(View child, int index, LayoutParams params) {
		throw new RuntimeException("Stub!");
	}

	public void updateViewLayout(View view, LayoutParams params) {
		throw new RuntimeException("Stub!");
	}

	protected boolean checkLayoutParams(LayoutParams p) {
		throw new RuntimeException("Stub!");
	}

	public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
		throw new RuntimeException("Stub!");
	}

	protected boolean addViewInLayout(View child, int index, LayoutParams params) {
		throw new RuntimeException("Stub!");
	}

	protected boolean addViewInLayout(View child, int index,
			LayoutParams params, boolean preventRequestLayout) {
		throw new RuntimeException("Stub!");
	}

	protected void cleanupLayoutState(View child) {
		throw new RuntimeException("Stub!");
	}

	protected void attachLayoutAnimationParameters(View child,
			LayoutParams params, int index, int count) {
		throw new RuntimeException("Stub!");
	}

	public void removeView(View view) {
		throw new RuntimeException("Stub!");
	}

	public void removeViewInLayout(View view) {
		throw new RuntimeException("Stub!");
	}

	public void removeViewsInLayout(int start, int count) {
		throw new RuntimeException("Stub!");
	}

	public void removeViewAt(int index) {
		throw new RuntimeException("Stub!");
	}

	public void removeViews(int start, int count) {
		throw new RuntimeException("Stub!");
	}

	public void setLayoutTransition(LayoutTransition transition) {
		throw new RuntimeException("Stub!");
	}

	public LayoutTransition getLayoutTransition() {
		throw new RuntimeException("Stub!");
	}

	public void removeAllViews() {
		throw new RuntimeException("Stub!");
	}

	public void removeAllViewsInLayout() {
		throw new RuntimeException("Stub!");
	}

	protected void removeDetachedView(View child, boolean animate) {
		throw new RuntimeException("Stub!");
	}

	protected void attachViewToParent(View child, int index, LayoutParams params) {
		throw new RuntimeException("Stub!");
	}

	protected void detachViewFromParent(View child) {
		throw new RuntimeException("Stub!");
	}

	protected void detachViewFromParent(int index) {
		throw new RuntimeException("Stub!");
	}

	protected void detachViewsFromParent(int start, int count) {
		throw new RuntimeException("Stub!");
	}

	protected void detachAllViewsFromParent() {
		throw new RuntimeException("Stub!");
	}

	public final void invalidateChild(View child, Rect dirty) {
		throw new RuntimeException("Stub!");
	}

	public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
		throw new RuntimeException("Stub!");
	}

	public final void offsetDescendantRectToMyCoords(View descendant, Rect rect) {
		throw new RuntimeException("Stub!");
	}

	public final void offsetRectIntoDescendantCoords(View descendant, Rect rect) {
		throw new RuntimeException("Stub!");
	}

	public boolean getChildVisibleRect(View child, Rect r, Point offset) {
		throw new RuntimeException("Stub!");
	}

	public final void layout(int l, int t, int r, int b) {
		throw new RuntimeException("Stub!");
	}

	protected abstract void onLayout(boolean paramBoolean, int paramInt1,
			int paramInt2, int paramInt3, int paramInt4);

	protected boolean canAnimate() {
		throw new RuntimeException("Stub!");
	}

	public void startLayoutAnimation() {
		throw new RuntimeException("Stub!");
	}

	public void scheduleLayoutAnimation() {
		throw new RuntimeException("Stub!");
	}

	public void setLayoutAnimation(LayoutAnimationController controller) {
		throw new RuntimeException("Stub!");
	}

	public LayoutAnimationController getLayoutAnimation() {
		throw new RuntimeException("Stub!");
	}

	@ViewDebug.ExportedProperty
	public boolean isAnimationCacheEnabled() {
		throw new RuntimeException("Stub!");
	}

	public void setAnimationCacheEnabled(boolean enabled) {
		throw new RuntimeException("Stub!");
	}

	@ViewDebug.ExportedProperty(category = "drawing")
	public boolean isAlwaysDrawnWithCacheEnabled() {
		throw new RuntimeException("Stub!");
	}

	public void setAlwaysDrawnWithCacheEnabled(boolean always) {
		throw new RuntimeException("Stub!");
	}

	@ViewDebug.ExportedProperty(category = "drawing")
	protected boolean isChildrenDrawnWithCacheEnabled() {
		throw new RuntimeException("Stub!");
	}

	protected void setChildrenDrawnWithCacheEnabled(boolean enabled) {
		throw new RuntimeException("Stub!");
	}

	@ViewDebug.ExportedProperty(category = "drawing")
	protected boolean isChildrenDrawingOrderEnabled() {
		throw new RuntimeException("Stub!");
	}

	protected void setChildrenDrawingOrderEnabled(boolean enabled) {
		throw new RuntimeException("Stub!");
	}

	@ViewDebug.ExportedProperty(category = "drawing", mapping = {
			@ViewDebug.IntToString(from = 0, to = "NONE"),
			@ViewDebug.IntToString(from = 1, to = "ANIMATION"),
			@ViewDebug.IntToString(from = 2, to = "SCROLLING"),
			@ViewDebug.IntToString(from = 3, to = "ALL") })
	public int getPersistentDrawingCache() {
		throw new RuntimeException("Stub!");
	}

	public void setPersistentDrawingCache(int drawingCacheToKeep) {
		throw new RuntimeException("Stub!");
	}

	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		throw new RuntimeException("Stub!");
	}

	protected LayoutParams generateLayoutParams(LayoutParams p) {
		throw new RuntimeException("Stub!");
	}

	protected LayoutParams generateDefaultLayoutParams() {
		throw new RuntimeException("Stub!");
	}

	protected void debug(int depth) {
		throw new RuntimeException("Stub!");
	}

	public int indexOfChild(View child) {
		throw new RuntimeException("Stub!");
	}

	public int getChildCount() {
		throw new RuntimeException("Stub!");
	}

	public View getChildAt(int index) {
		throw new RuntimeException("Stub!");
	}

	protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
		throw new RuntimeException("Stub!");
	}

	protected void measureChild(View child, int parentWidthMeasureSpec,
			int parentHeightMeasureSpec) {
		throw new RuntimeException("Stub!");
	}

	protected void measureChildWithMargins(View child,
			int parentWidthMeasureSpec, int widthUsed,
			int parentHeightMeasureSpec, int heightUsed) {
		throw new RuntimeException("Stub!");
	}

	public static int getChildMeasureSpec(int spec, int padding,
			int childDimension) {
		throw new RuntimeException("Stub!");
	}

	public void clearDisappearingChildren() {
		throw new RuntimeException("Stub!");
	}

	public void startViewTransition(View view) {
		throw new RuntimeException("Stub!");
	}

	public void endViewTransition(View view) {
		throw new RuntimeException("Stub!");
	}

	public boolean gatherTransparentRegion(Region region) {
		throw new RuntimeException("Stub!");
	}

	public void requestTransparentRegion(View child) {
		throw new RuntimeException("Stub!");
	}

	protected boolean fitSystemWindows(Rect insets) {
		throw new RuntimeException("Stub!");
	}

	public Animation.AnimationListener getLayoutAnimationListener() {
		throw new RuntimeException("Stub!");
	}

	protected void drawableStateChanged() {
		throw new RuntimeException("Stub!");
	}

	public void jumpDrawablesToCurrentState() {
		throw new RuntimeException("Stub!");
	}

	protected int[] onCreateDrawableState(int extraSpace) {
		throw new RuntimeException("Stub!");
	}

	public void setAddStatesFromChildren(boolean addsStates) {
		throw new RuntimeException("Stub!");
	}

	public boolean addStatesFromChildren() {
		throw new RuntimeException("Stub!");
	}

	public void childDrawableStateChanged(View child) {
		throw new RuntimeException("Stub!");
	}

	public void setLayoutAnimationListener(
			Animation.AnimationListener animationListener) {
		throw new RuntimeException("Stub!");
	}

	protected void resetResolvedLayoutDirection() {
		throw new RuntimeException("Stub!");
	}

	protected void resetResolvedTextDirection() {
		throw new RuntimeException("Stub!");
	}

	public boolean shouldDelayChildPressedState() {
		throw new RuntimeException("Stub!");
	}

	public void requestFitSystemWindows() {
		throw new RuntimeException("Stub!");
	}

	public ViewParent getParentForAccessibility() {
		throw new RuntimeException("Stub!");
	}

	public static class MarginLayoutParams extends ViewGroup.LayoutParams {

		@ViewDebug.ExportedProperty(category = "layout")
		public int leftMargin;

		@ViewDebug.ExportedProperty(category = "layout")
		public int topMargin;

		@ViewDebug.ExportedProperty(category = "layout")
		public int rightMargin;

		@ViewDebug.ExportedProperty(category = "layout")
		public int bottomMargin;

		public MarginLayoutParams(Context c, AttributeSet attrs) {
			super(c, attrs);
			throw new RuntimeException("Stub!");
		}

		public MarginLayoutParams(int width, int height) {
			super(width, height);
			throw new RuntimeException("Stub!");
		}

		public MarginLayoutParams(MarginLayoutParams source) {
			super(source);
			throw new RuntimeException("Stub!");
		}

		public MarginLayoutParams(ViewGroup.LayoutParams source) {
			super(source);
			throw new RuntimeException("Stub!");
		}

		public void setMargins(int left, int top, int right, int bottom) {
			throw new RuntimeException("Stub!");
		}

	}

	public static class LayoutParams {

		@Deprecated
		public static final int FILL_PARENT = -1;
		public static final int MATCH_PARENT = -1;
		public static final int WRAP_CONTENT = -2;

		@ViewDebug.ExportedProperty(category = "layout", mapping = {
				@ViewDebug.IntToString(from = -1, to = "MATCH_PARENT"),
				@ViewDebug.IntToString(from = -2, to = "WRAP_CONTENT") })
		public int width;

		@ViewDebug.ExportedProperty(category = "layout", mapping = {
				@ViewDebug.IntToString(from = -1, to = "MATCH_PARENT"),
				@ViewDebug.IntToString(from = -2, to = "WRAP_CONTENT") })
		public int height;
		public LayoutAnimationController.AnimationParameters layoutAnimationParameters;

		public LayoutParams(Context c, AttributeSet attrs) {
			throw new RuntimeException("Stub!");
		}

		public LayoutParams(int width, int height) {
			throw new RuntimeException("Stub!");
		}

		public LayoutParams(LayoutParams source) {
			throw new RuntimeException("Stub!");
		}

		protected void setBaseAttributes(TypedArray a, int widthAttr,
				int heightAttr) {
			throw new RuntimeException("Stub!");
		}

	}

	public static abstract interface OnHierarchyChangeListener {
		public abstract void onChildViewAdded(View paramView1, View paramView2);

		public abstract void onChildViewRemoved(View paramView1, View paramView2);
	}

	/**
	 * Internal flags.
	 * 
	 * This field should be made private, so it is hidden from the SDK. {@hide
	 * }
	 */
	protected int mGroupFlags;

	/**
	 * Indicates which types of drawing caches are to be kept in memory. This
	 * field should be made private, so it is hidden from the SDK. {@hide
	 * }
	 */
	protected int mPersistentDrawingCache;

	/**
	 * Offset the vertical location of all children of this view by the
	 * specified number of pixels.
	 * 
	 * @param offset
	 *            the number of pixels to offset
	 * 
	 * @hide
	 */
	public void offsetChildrenTopAndBottom(int offset) {
	}

	/**
	 * Have the parent populate the specified context menu if it has anything to
	 * add (and then recurse on its parent).
	 *
	 * @param menu The menu to populate
	 */
	public void createContextMenu(ContextMenu menu) {
		throw new RuntimeException("Stub!");
	}



	/**
	 * Start an action mode of a specific type for the specified view.
	 *
	 * <p>In most cases, a subclass does not need to override this. However, if the
	 * subclass is added directly to the window manager (for example,
	 * {@link ViewManager#addView(View, android.view.ViewGroup.LayoutParams)})
	 * then it should override this and start the action mode.</p>
	 *
	 * @param originalView The source view where the action mode was first invoked
	 * @param callback The callback that will handle lifecycle events for the action mode
	 * @param type One of {@link ActionMode#TYPE_PRIMARY} or {@link ActionMode#TYPE_FLOATING}.
	 * @return The new action mode if it was started, null otherwise
	 */
	public ActionMode startActionModeForChild(
			View originalView, ActionMode.Callback callback, int type) {
		throw new RuntimeException("Stub!");
	}







	/**
	 * Called when a child view now has or no longer is tracking transient state.
	 *
	 * <p>"Transient state" is any state that a View might hold that is not expected to
	 * be reflected in the data model that the View currently presents. This state only
	 * affects the presentation to the user within the View itself, such as the current
	 * state of animations in progress or the state of a text selection operation.</p>
	 *
	 * <p>Transient state is useful for hinting to other components of the View system
	 * that a particular view is tracking something complex but encapsulated.
	 * A <code>ListView</code> for example may acknowledge that list item Views
	 * with transient state should be preserved within their position or stable item ID
	 * instead of treating that view as trivially replaceable by the backing adapter.
	 * This allows adapter implementations to be simpler instead of needing to track
	 * the state of item view animations in progress such that they could be restored
	 * in the event of an unexpected recycling and rebinding of attached item views.</p>
	 *
	 * <p>This method is called on a parent view when a child view or a view within
	 * its subtree begins or ends tracking of internal transient state.</p>
	 *
	 * @param child Child view whose state has changed
	 * @param hasTransientState true if this child has transient state
	 */
	public void childHasTransientStateChanged(View child, boolean hasTransientState) {
		throw new RuntimeException("Stub!");
	}

	/**
	 * Notifies a view parent that the accessibility state of one of its
	 * descendants has changed and that the structure of the subtree is
	 * different.
	 * @param child The direct child whose subtree has changed.
	 * @param source The descendant view that changed.
	 * @param changeType A bit mask of the types of changes that occurred. One
	 *            or more of:
	 *            <ul>
	 *            <li>{@link AccessibilityEvent#CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION}
	 *            <li>{@link AccessibilityEvent#CONTENT_CHANGE_TYPE_SUBTREE}
	 *            <li>{@link AccessibilityEvent#CONTENT_CHANGE_TYPE_TEXT}
	 *            <li>{@link AccessibilityEvent#CONTENT_CHANGE_TYPE_UNDEFINED}
	 *            </ul>
	 */
	public void notifySubtreeAccessibilityStateChanged(View child, View source, int changeType) {
		throw new RuntimeException("Stub!");
	}

	/**
	 * Tells if this view parent can resolve the layout direction.
	 * See {@link View#setLayoutDirection(int)}
	 *
	 * @return True if this view parent can resolve the layout direction.
	 */
	@SuppressWarnings("all")
	public boolean canResolveLayoutDirection() {
		throw new RuntimeException("Stub!");
	}

	/**
	 * Tells if this view parent layout direction is resolved.
	 * See {@link View#setLayoutDirection(int)}
	 *
	 * @return True if this view parent layout direction is resolved.
	 */
	@SuppressWarnings("all")
	public boolean isLayoutDirectionResolved() {
		throw new RuntimeException("Stub!");
	}

	/**
	 * Return this view parent layout direction. See {@link View#getLayoutDirection()}
	 *
	 * @return {@link View#LAYOUT_DIRECTION_RTL} if the layout direction is RTL or returns
	 * {@link View#LAYOUT_DIRECTION_LTR} if the layout direction is not RTL.
	 */
	@SuppressWarnings("all")
	public int getLayoutDirection() {
		throw new RuntimeException("Stub!");
	}

	/**
	 * Tells if this view parent can resolve the text direction.
	 * See {@link View#setTextDirection(int)}
	 *
	 * @return True if this view parent can resolve the text direction.
	 */
	@SuppressWarnings("all")
	public boolean canResolveTextDirection() {
		throw new RuntimeException("Stub!");
	}

	/**
	 * Tells if this view parent text direction is resolved.
	 * See {@link View#setTextDirection(int)}
	 *
	 * @return True if this view parent text direction is resolved.
	 */
	@SuppressWarnings("all")
	public boolean isTextDirectionResolved() {
		throw new RuntimeException("Stub!");
	}

	/**
	 * Return this view parent text direction. See {@link View#getTextDirection()}
	 *
	 * @return the resolved text direction. Returns one of:
	 *
	 * {@link View#TEXT_DIRECTION_FIRST_STRONG}
	 * {@link View#TEXT_DIRECTION_ANY_RTL},
	 * {@link View#TEXT_DIRECTION_LTR},
	 * {@link View#TEXT_DIRECTION_RTL},
	 * {@link View#TEXT_DIRECTION_LOCALE}
	 */
	@SuppressWarnings("all")
	public int getTextDirection() {
		throw new RuntimeException("Stub!");
	}

	/**
	 * Tells if this view parent can resolve the text alignment.
	 * See {@link View#setTextAlignment(int)}
	 *
	 * @return True if this view parent can resolve the text alignment.
	 */
	@SuppressWarnings("all")
	public boolean canResolveTextAlignment() {
		throw new RuntimeException("Stub!");
	}

	/**
	 * Tells if this view parent text alignment is resolved.
	 * See {@link View#setTextAlignment(int)}
	 *
	 * @return True if this view parent text alignment is resolved.
	 */
	@SuppressWarnings("all")
	public boolean isTextAlignmentResolved() {
		throw new RuntimeException("Stub!");
	}

	/**
	 * Return this view parent text alignment. See {@link android.view.View#getTextAlignment()}
	 *
	 * @return the resolved text alignment. Returns one of:
	 *
	 * {@link View#TEXT_ALIGNMENT_GRAVITY},
	 * {@link View#TEXT_ALIGNMENT_CENTER},
	 * {@link View#TEXT_ALIGNMENT_TEXT_START},
	 * {@link View#TEXT_ALIGNMENT_TEXT_END},
	 * {@link View#TEXT_ALIGNMENT_VIEW_START},
	 * {@link View#TEXT_ALIGNMENT_VIEW_END}
	 */
	@SuppressWarnings("all")
	public int getTextAlignment() {
		throw new RuntimeException("Stub!");
	}

	/**
	 * React to a descendant view initiating a nestable scroll operation, claiming the
	 * nested scroll operation if appropriate.
	 *
	 * <p>This method will be called in response to a descendant view invoking
	 * {@link View#startNestedScroll(int)}. Each parent up the view hierarchy will be
	 * given an opportunity to respond and claim the nested scrolling operation by returning
	 * <code>true</code>.</p>
	 *
	 * <p>This method may be overridden by ViewParent implementations to indicate when the view
	 * is willing to support a nested scrolling operation that is about to begin. If it returns
	 * true, this ViewParent will become the target view's nested scrolling parent for the duration
	 * of the scroll operation in progress. When the nested scroll is finished this ViewParent
	 * will receive a call to {@link #onStopNestedScroll(View)}.
	 * </p>
	 *
	 * @param child Direct child of this ViewParent containing target
	 * @param target View that initiated the nested scroll
	 * @param nestedScrollAxes Flags consisting of {@link View#SCROLL_AXIS_HORIZONTAL},
	 *                         {@link View#SCROLL_AXIS_VERTICAL} or both
	 * @return true if this ViewParent accepts the nested scroll operation
	 */
	public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
		throw new RuntimeException("Stub!");
	}

	/**
	 * React to the successful claiming of a nested scroll operation.
	 *
	 * <p>This method will be called after
	 * {@link #onStartNestedScroll(View, View, int) onStartNestedScroll} returns true. It offers
	 * an opportunity for the view and its superclasses to perform initial configuration
	 * for the nested scroll. Implementations of this method should always call their superclass's
	 * implementation of this method if one is present.</p>
	 *
	 * @param child Direct child of this ViewParent containing target
	 * @param target View that initiated the nested scroll
	 * @param nestedScrollAxes Flags consisting of {@link View#SCROLL_AXIS_HORIZONTAL},
	 *                         {@link View#SCROLL_AXIS_VERTICAL} or both
	 * @see #onStartNestedScroll(View, View, int)
	 * @see #onStopNestedScroll(View)
	 */
	public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
		throw new RuntimeException("Stub!");
	}

	/**
	 * React to a nested scroll operation ending.
	 *
	 * <p>Perform cleanup after a nested scrolling operation.
	 * This method will be called when a nested scroll stops, for example when a nested touch
	 * scroll ends with a {@link MotionEvent#ACTION_UP} or {@link MotionEvent#ACTION_CANCEL} event.
	 * Implementations of this method should always call their superclass's implementation of this
	 * method if one is present.</p>
	 *
	 * @param target View that initiated the nested scroll
	 */
	public void onStopNestedScroll(View target) {
		throw new RuntimeException("Stub!");
	}

	/**
	 * React to a nested scroll in progress.
	 *
	 * <p>This method will be called when the ViewParent's current nested scrolling child view
	 * dispatches a nested scroll event. To receive calls to this method the ViewParent must have
	 * previously returned <code>true</code> for a call to
	 * {@link #onStartNestedScroll(View, View, int)}.</p>
	 *
	 * <p>Both the consumed and unconsumed portions of the scroll distance are reported to the
	 * ViewParent. An implementation may choose to use the consumed portion to match or chase scroll
	 * position of multiple child elements, for example. The unconsumed portion may be used to
	 * allow continuous dragging of multiple scrolling or draggable elements, such as scrolling
	 * a list within a vertical drawer where the drawer begins dragging once the edge of inner
	 * scrolling content is reached.</p>
	 *
	 * @param target The descendent view controlling the nested scroll
	 * @param dxConsumed Horizontal scroll distance in pixels already consumed by target
	 * @param dyConsumed Vertical scroll distance in pixels already consumed by target
	 * @param dxUnconsumed Horizontal scroll distance in pixels not consumed by target
	 * @param dyUnconsumed Vertical scroll distance in pixels not consumed by target
	 */
	public void onNestedScroll(View target, int dxConsumed, int dyConsumed,
							   int dxUnconsumed, int dyUnconsumed) {
		throw new RuntimeException("Stub!");
	}

	/**
	 * React to a nested scroll in progress before the target view consumes a portion of the scroll.
	 *
	 * <p>When working with nested scrolling often the parent view may want an opportunity
	 * to consume the scroll before the nested scrolling child does. An example of this is a
	 * drawer that contains a scrollable list. The user will want to be able to scroll the list
	 * fully into view before the list itself begins scrolling.</p>
	 *
	 * <p><code>onNestedPreScroll</code> is called when a nested scrolling child invokes
	 * {@link View#dispatchNestedPreScroll(int, int, int[], int[])}. The implementation should
	 * report how any pixels of the scroll reported by dx, dy were consumed in the
	 * <code>consumed</code> array. Index 0 corresponds to dx and index 1 corresponds to dy.
	 * This parameter will never be null. Initial values for consumed[0] and consumed[1]
	 * will always be 0.</p>
	 *
	 * @param target View that initiated the nested scroll
	 * @param dx Horizontal scroll distance in pixels
	 * @param dy Vertical scroll distance in pixels
	 * @param consumed Output. The horizontal and vertical scroll distance consumed by this parent
	 */
	public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
		throw new RuntimeException("Stub!");
	}

	/**
	 * Request a fling from a nested scroll.
	 *
	 * <p>This method signifies that a nested scrolling child has detected suitable conditions
	 * for a fling. Generally this means that a touch scroll has ended with a
	 * {@link VelocityTracker velocity} in the direction of scrolling that meets or exceeds
	 * the {@link ViewConfiguration#getScaledMinimumFlingVelocity() minimum fling velocity}
	 * along a scrollable axis.</p>
	 *
	 * <p>If a nested scrolling child view would normally fling but it is at the edge of
	 * its own content, it can use this method to delegate the fling to its nested scrolling
	 * parent instead. The parent may optionally consume the fling or observe a child fling.</p>
	 *
	 * @param target View that initiated the nested scroll
	 * @param velocityX Horizontal velocity in pixels per second
	 * @param velocityY Vertical velocity in pixels per second
	 * @param consumed true if the child consumed the fling, false otherwise
	 * @return true if this parent consumed or otherwise reacted to the fling
	 */
	public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
		throw new RuntimeException("Stub!");
	}

	/**
	 * React to a nested fling before the target view consumes it.
	 *
	 * <p>This method siginfies that a nested scrolling child has detected a fling with the given
	 * velocity along each axis. Generally this means that a touch scroll has ended with a
	 * {@link VelocityTracker velocity} in the direction of scrolling that meets or exceeds
	 * the {@link ViewConfiguration#getScaledMinimumFlingVelocity() minimum fling velocity}
	 * along a scrollable axis.</p>
	 *
	 * <p>If a nested scrolling parent is consuming motion as part of a
	 * {@link #onNestedPreScroll(View, int, int, int[]) pre-scroll}, it may be appropriate for
	 * it to also consume the pre-fling to complete that same motion. By returning
	 * <code>true</code> from this method, the parent indicates that the child should not
	 * fling its own internal content as well.</p>
	 *
	 * @param target View that initiated the nested scroll
	 * @param velocityX Horizontal velocity in pixels per second
	 * @param velocityY Vertical velocity in pixels per second
	 * @return true if this parent consumed the fling ahead of the target view
	 */
	public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
		throw new RuntimeException("Stub!");
	}

	/**
	 * React to an accessibility action delegated by a target descendant view before the target
	 * processes it.
	 *
	 * <p>This method may be called by a target descendant view if the target wishes to give
	 * a view in its parent chain a chance to react to the event before normal processing occurs.
	 * Most commonly this will be a scroll event such as
	 * {@link android.view.accessibility.AccessibilityNodeInfo#ACTION_SCROLL_FORWARD}.
	 * A ViewParent that supports acting as a nested scrolling parent should override this
	 * method and act accordingly to implement scrolling via accesibility systems.</p>
	 *
	 * @param target The target view dispatching this action
	 * @param action Action being performed; see
	 *               {@link android.view.accessibility.AccessibilityNodeInfo}
	 * @param arguments Optional action arguments
	 * @return true if the action was consumed by this ViewParent
	 */
	public boolean onNestedPrePerformAccessibilityAction(View target, int action, Bundle arguments) {
		throw new RuntimeException("Stub!");
	}

	public boolean showContextMenuForChild(View originlView, float x, float y) {
		throw new RuntimeException("Stub!");
	}

	public View keyboardNavigationClusterSearch(View currentCluster, int direction) {
		throw new RuntimeException("Stub!");
	}

}