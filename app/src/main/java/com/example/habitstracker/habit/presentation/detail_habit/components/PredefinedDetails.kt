package com.example.habitstracker.habit.presentation.detail_habit.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.AirlineSeatReclineNormal
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.CoffeeMaker
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.DoNotDisturb
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NaturePeople
import androidx.compose.material.icons.filled.NoFood
import androidx.compose.material.icons.filled.NoMeals
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.PhoneDisabled
import androidx.compose.material.icons.filled.PhoneLocked
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Podcasts
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ScreenLockLandscape
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.SmokingRooms
import androidx.compose.material.icons.filled.SocialDistance
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WbSunny
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.UiText
import com.example.habitstracker.core.presentation.theme.HabitColor

val keepActiveGetFit = listOf(
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.workout),
        UiText.StringResources(R.string.move_your_body_and_enjoy_burning_fat),
        Icons.Default.FitnessCenter,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.walking),
        UiText.StringResources(R.string.boost_your_steps_daily),
        Icons.Default.DirectionsWalk,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.running),
        UiText.StringResources(R.string.increase_stamina_and_energy),
        Icons.Default.DirectionsRun,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.yoga),
        UiText.StringResources(R.string.improve_flexibility_and_calm_your_mind),
        Icons.Default.SelfImprovement,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.swimming),
        UiText.StringResources(R.string.full_body_workout_without_sweat),
        Icons.Default.Pool,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.stretching),
        UiText.StringResources(R.string.release_tension_and_increase_flexibility),
        Icons.Default.Accessibility,
        HabitColor.Amber.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.cycling),
        UiText.StringResources(R.string.strengthen_legs_and_explore_outdoors),
        Icons.Default.PedalBike,
        HabitColor.SkyBlue.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.hiking),
        UiText.StringResources(R.string.embrace_nature_and_stay_active),
        Icons.Default.Terrain,
        HabitColor.LeafGreen.light
    ),
)

val eatDrinkHealthily = listOf(
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.no_alcohol),
        UiText.StringResources(R.string.life_is_way_better_without_alcohol),
        Icons.Default.LocalBar,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.no_fried_food),
        UiText.StringResources(R.string.an_easy_way_to_lose_fat),
        Icons.Default.Fastfood,
        HabitColor.Amber.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.go_sugar_free),
        UiText.StringResources(R.string.see_your_body_change_with_being_sugar_free),
        Icons.Default.NoMeals,
        HabitColor.SkyBlue.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.limit_caffeine_intake),
        UiText.StringResources(R.string.replace_with_other_healthy_choices),
        Icons.Default.CoffeeMaker,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.monitor_your_portion_size),
        UiText.StringResources(R.string.keep_your_servings_balanced),
        Icons.Default.Scale,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.intermittent_fasting),
        UiText.StringResources(R.string.your_stomach_needs_a_little_rest),
        Icons.Default.Timer,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.take_greens_fruits),
        UiText.StringResources(R.string.dont_forget_the_natural_snacks),
        Icons.Default.Eco,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.have_breakfast),
        UiText.StringResources(R.string.wake_up_your_body_with_energy),
        Icons.Default.BreakfastDining,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.drink_enough_water),
        UiText.StringResources(R.string.keep_your_body_hydrated),
        Icons.Default.LocalDrink,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.prepare_home_cooked_meals),
        UiText.StringResources(R.string.control_ingredients_and_portions),
        Icons.Default.Kitchen,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.avoid_processed_food),
        UiText.StringResources(R.string.stick_to_whole_and_fresh_ingredients),
        Icons.Default.Warning,
        HabitColor.BrickRed.light
    )
)

val easeStress = listOf(
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.practice_meditation),
        UiText.StringResources(R.string.connect_with_your_inner_peace),
        Icons.Default.SelfImprovement,
        HabitColor.SkyBlue.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.practice_breathing),
        UiText.StringResources(R.string.calm_yourself_from_every_in_and_out),
        Icons.Default.Air,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.read_a_book),
        UiText.StringResources(R.string.let_your_mind_wander_through_stories),
        Icons.Default.MenuBook,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.keep_a_journal),
        UiText.StringResources(R.string.know_yourself_better_through_writing),
        Icons.Default.Create,
        HabitColor.Rose.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.do_stretching_exercises),
        UiText.StringResources(R.string.release_tension_and_stress),
        Icons.Default.FitnessCenter,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.do_yoga),
        UiText.StringResources(R.string.calming_and_smoothing),
        Icons.Default.SelfImprovement,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.get_enough_sleep),
        UiText.StringResources(R.string.allow_your_body_to_recover_and_recharge),
        Icons.Default.Bedtime,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.enjoy_nature),
        UiText.StringResources(R.string.embrace_nature_to_calm_your_mind),
        Icons.Default.NaturePeople,
        HabitColor.Lime.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.disconnect_from_devices),
        UiText.StringResources(R.string.take_a_break_from_screens_and_notifications),
        Icons.Default.PhoneDisabled,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.practice_gratitude),
        UiText.StringResources(R.string.focus_on_positive_aspects_of_your_life),
        Icons.Default.Favorite,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.take_a_warm_bath),
        UiText.StringResources(R.string.feel_the_healing_power_of_relaxation),
        Icons.Default.Bathtub,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.listen_to_calming_music),
        UiText.StringResources(R.string.let_soothing_melodies_relax_you),
        Icons.Default.MusicNote,
        HabitColor.Purple.light
    )
)

val gainSelfDiscipline = listOf(
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.limit_screen_time),
        UiText.StringResources(R.string.reduce_distractions_by_controlling_screen_use),
        Icons.Default.ScreenLockLandscape,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.no_emotional_spending),
        UiText.StringResources(R.string.distinguish_your_needs_and_wants),
        Icons.Default.AttachMoney,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.go_sugar_free),
        UiText.StringResources(R.string.see_your_body_change_with_being_sugar_free),
        Icons.Default.NoFood,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.avoid_alcohol),
        UiText.StringResources(R.string.life_is_way_better_without_alcohol),
        Icons.Default.LocalBar,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.limit_caffeine_intake),
        UiText.StringResources(R.string.replace_with_other_healthy_choices),
        Icons.Default.Coffee,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.quit_smoking),
        UiText.StringResources(R.string.dont_have_just_one),
        Icons.Default.SmokingRooms,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.avoid_fried_food),
        UiText.StringResources(R.string.an_easy_way_to_lose_fat),
        Icons.Default.Fastfood,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.no_excessive_gaming),
        UiText.StringResources(R.string.winners_dont_just_exist_in_games),
        Icons.Default.VideogameAsset,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.wake_up_early),
        UiText.StringResources(R.string.start_your_day_with_a_productive_mindset),
        Icons.Default.WbSunny,
        HabitColor.Golden.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.create_a_to_do_list),
        UiText.StringResources(R.string.plan_your_day_to_avoid_procrastination),
        Icons.Default.List,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.set_small_goals),
        UiText.StringResources(R.string.achieve_success_step_by_step),
        Icons.Default.Lightbulb,
        HabitColor.Amber.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.keep_a_daily_journal),
        UiText.StringResources(R.string.track_your_progress_and_self_growth),
        Icons.Default.Edit,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.clean_up_your_workspace),
        UiText.StringResources(R.string.less_mess_equals_less_stress),
        Icons.Default.CleaningServices,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.practice_delayed_gratification),
        UiText.StringResources(R.string.train_yourself_to_resist_instant_pleasures),
        Icons.Default.HourglassEmpty,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.stay_hydrated),
        UiText.StringResources(R.string.drink_water_to_maintain_focus_and_energy),
        Icons.Default.LocalDrink,
        HabitColor.SkyBlue.light
    )
)

val leisureMoments = listOf(
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.practice_a_hobby),
        UiText.StringResources(R.string.fill_your_time_life_wont_pass_you_by),
        Icons.Default.Brush,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.learn_a_musical_instrument),
        UiText.StringResources(R.string.run_your_fingers_through_your_soul),
        Icons.Default.MusicNote,
        HabitColor.Rose.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.learn_a_language),
        UiText.StringResources(R.string.deepen_your_connection_to_other_cultures),
        Icons.Default.Language,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.watch_a_movie),
        UiText.StringResources(R.string.get_yourself_a_film_therapy),
        Icons.Default.Movie,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.take_a_photo),
        UiText.StringResources(R.string.record_it_as_lifetime_memories),
        Icons.Default.PhotoCamera,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.time_for_self_care),
        UiText.StringResources(R.string.take_time_to_nourish_your_body_properly),
        Icons.Default.Spa,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.read_a_book),
        UiText.StringResources(R.string.lose_yourself_in_another_world),
        Icons.Default.Book,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.go_for_a_nature_walk),
        UiText.StringResources(R.string.reconnect_with_nature_and_yourself),
        Icons.Default.Forest,
        HabitColor.Lime.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.attend_a_live_event),
        UiText.StringResources(R.string.enjoy_live_music_theater_or_sports),
        Icons.Default.Event,
        HabitColor.Rose.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.call_a_friend),
        UiText.StringResources(R.string.strengthen_your_connections),
        Icons.Default.Call,
        HabitColor.Teal.light
    )
)

val goodMorningHabits = listOf(
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.get_up_early),
        UiText.StringResources(R.string.shine_before_the_sun_does),
        Icons.Default.WbSunny,
        HabitColor.Golden.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.dress_up),
        UiText.StringResources(R.string.a_good_look_lights_up_your_day),
        Icons.Default.Checkroom,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.have_breakfast),
        UiText.StringResources(R.string.wake_up_your_body_with_energy),
        Icons.Default.Restaurant,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.make_the_bed),
        UiText.StringResources(R.string.clean_your_bed_clear_your_mind),
        Icons.Default.Bed,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.empty_trash),
        UiText.StringResources(R.string.leave_home_with_no_mess),
        Icons.Default.Delete,
        HabitColor.Rose.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.warm_up),
        UiText.StringResources(R.string.a_natural_morning_pick_me_up),
        Icons.Default.FitnessCenter,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.plan_your_day),
        UiText.StringResources(R.string.set_your_intentions_and_priorities),
        Icons.Default.CalendarToday,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.meditate),
        UiText.StringResources(R.string.connect_with_your_inner_peace),
        Icons.Default.SelfImprovement,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.hydrate),
        UiText.StringResources(R.string.start_your_day_with_a_glass_of_water),
        Icons.Default.LocalDrink,
        HabitColor.SkyBlue.light
    ),
)

val beforeSleepRoutineHabits = listOf(
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.stop_eating_before_bed),
        UiText.StringResources(R.string.your_stomach_also_needs_a_good_sleep),
        Icons.Default.NoFood,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.go_to_bed_early),
        UiText.StringResources(R.string.early_to_bed_early_to_rise),
        Icons.Default.Bedtime,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.practice_meditation),
        UiText.StringResources(R.string.connect_with_your_inner_peace),
        Icons.Default.SelfImprovement,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.practice_breathing),
        UiText.StringResources(R.string.calm_yourself_from_every_in_and_out),
        Icons.Default.Air,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.keep_a_journal),
        UiText.StringResources(R.string.reflect_on_your_day_and_release_thoughts),
        Icons.Default.EditNote,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.read_a_book),
        UiText.StringResources(R.string.never_enough_books_before_bedtime),
        Icons.Default.MenuBook,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.take_a_bath),
        UiText.StringResources(R.string.feel_the_healing_power_of_a_warm_bath),
        Icons.Default.Bathtub,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.set_tomorrows_plan),
        UiText.StringResources(R.string.organize_your_tasks_for_a_smooth_day_ahead),
        Icons.Default.EventNote,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.dim_the_lights),
        UiText.StringResources(R.string.create_a_calming_atmosphere),
        Icons.Default.LightMode,
        HabitColor.Golden.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.turn_off_screens),
        UiText.StringResources(R.string.disconnect_and_let_your_mind_unwind),
        Icons.Default.DoNotDisturb,
        HabitColor.BrickRed.light
    ),
)

val masterProductivityHabits = listOf(
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.set_small_goals),
        UiText.StringResources(R.string.make_goals_more_specific_and_achievable),
        Icons.Default.TipsAndUpdates,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.prepare_tomorrows_to_do_list),
        UiText.StringResources(R.string.make_your_time_visible_and_countable),
        Icons.Default.List,
        HabitColor.Lime.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.clean_up_workspace),
        UiText.StringResources(R.string.less_mess_equals_less_stress),
        Icons.Default.CleaningServices,
        HabitColor.Lime.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.block_screen_time),
        UiText.StringResources(R.string.lock_screen_and_glue_attention),
        Icons.Default.PhoneLocked,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.get_up_early),
        UiText.StringResources(R.string.shine_before_the_sun_does),
        Icons.Default.WbSunny,
        HabitColor.Amber.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.check_all_unread),
        UiText.StringResources(R.string.zero_your_inbox),
        Icons.Default.Mail,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.focus_on_one_task),
        UiText.StringResources(R.string.avoid_multitasking_to_increase_efficiency),
        Icons.Default.CenterFocusStrong,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.track_your_progress),
        UiText.StringResources(R.string.measure_your_achievements_daily),
        Icons.Default.TrendingUp,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.take_regular_breaks),
        UiText.StringResources(R.string.stay_productive_without_burnout),
        Icons.Default.AirlineSeatReclineNormal,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.set_priorities),
        UiText.StringResources(R.string.handle_the_most_important_tasks_first),
        Icons.Default.PriorityHigh,
        HabitColor.Rose.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.review_your_goals),
        UiText.StringResources(R.string.adjust_your_plans_for_continuous_improvement),
        Icons.Default.AssignmentTurnedIn,
        HabitColor.Teal.light
    )
)

val strongerMindHabits = listOf(
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.read),
        UiText.StringResources(R.string.expand_your_knowledge_and_creativity),
        Icons.Default.MenuBook,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.meditate),
        UiText.StringResources(R.string.improve_focus_and_reduce_anxiety),
        Icons.Default.SelfImprovement,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.learn_something_new),
        UiText.StringResources(R.string.expand_your_horizons_daily),
        Icons.Default.School,
        HabitColor.Amber.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.keep_a_journal),
        UiText.StringResources(R.string.reflect_on_your_thoughts_and_experiences),
        Icons.Default.Edit,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.practice_gratitude),
        UiText.StringResources(R.string.appreciate_the_good_things_in_your_life),
        Icons.Default.ThumbUp,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.solve_puzzles),
        UiText.StringResources(R.string.keep_your_brain_sharp_and_active),
        Icons.Default.Extension,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.avoid_negative_thoughts),
        UiText.StringResources(R.string.train_your_mind_to_focus_on_positivity),
        Icons.Default.RemoveCircle,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.practice_deep_breathing),
        UiText.StringResources(R.string.calm_your_mind_and_body),
        Icons.Default.Air,
        HabitColor.SkyBlue.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.develop_critical_thinking),
        UiText.StringResources(R.string.question_and_analyze_everything_critically),
        Icons.Default.Analytics,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.limit_social_media),
        UiText.StringResources(R.string.protect_your_mind_from_digital_overload),
        Icons.Default.SocialDistance,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.listen_to_podcasts),
        UiText.StringResources(R.string.gain_new_perspectives_and_insights),
        Icons.Default.Podcasts,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.learn_language),
        UiText.StringResources(R.string.enhance_your_cognitive_abilities),
        Icons.Default.Language,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        UiText.StringResources(R.string.maintain_a_balanced_diet),
        UiText.StringResources(R.string.fuel_your_brain_with_healthy_nutrients),
        Icons.Default.NoMeals,
        HabitColor.Sand.light
    )
)