package com.example.habitstracker.habit.presentation.detail_habit.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.AirlineSeatReclineNormal
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Cloud
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
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NaturePeople
import androidx.compose.material.icons.filled.NoFood
import androidx.compose.material.icons.filled.NoMeals
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Paragliding
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.PhoneDisabled
import androidx.compose.material.icons.filled.PhoneLocked
import androidx.compose.material.icons.filled.PhonelinkOff
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Podcasts
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ScreenLockLandscape
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.SmokingRooms
import androidx.compose.material.icons.filled.SocialDistance
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.SportsVolleyball
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WbSunny
import com.example.habitstracker.core.presentation.theme.HabitColor


val keepActiveGetFit = listOf(
    DefaultHabitDetailItem(
        "Workout",
        "Move your body and enjoy burning fat",
        Icons.Default.FitnessCenter,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        "Walking",
        "Boost your steps daily",
        Icons.Default.DirectionsWalk,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "Running",
        "Increase stamina and energy",
        Icons.Default.DirectionsRun,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        "Yoga",
        "Improve flexibility and calm your mind",
        Icons.Default.SelfImprovement,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        "Swimming",
        "Full body workout without sweat",
        Icons.Default.Pool,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        "Stretching",
        "Release tension and increase flexibility",
        Icons.Default.Accessibility,
        HabitColor.Amber.light
    ),
    DefaultHabitDetailItem(
        "Cycling",
        "Strengthen legs and explore outdoors",
        Icons.Default.PedalBike,
        HabitColor.SkyBlue.light
    ),
    DefaultHabitDetailItem(
        "Jump rope",
        "Boost your cardio and coordination",
        Icons.Default.SportsVolleyball,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        "Hiking",
        "Embrace nature and stay active",
        Icons.Default.Terrain,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "Push-ups",
        "Increase upper body strength",
        Icons.Default.PushPin,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        "Plank challenge",
        "Strengthen your core",
        Icons.Default.Build,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        "Block screen time",
        "Lock screen and glue attention",
        Icons.Default.PhonelinkOff,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        "Sleep over 8h",
        "Enough time for sweet dreams",
        Icons.Default.Hotel,
        HabitColor.Golden.light
    )
)

val eatDrinkHealthily = listOf(
    DefaultHabitDetailItem(
        "Go sugar-free",
        "See your body change with being sugar-free",
        Icons.Default.NoMeals,
        HabitColor.SkyBlue.light
    ),
    DefaultHabitDetailItem(
        "Limit caffeine intake",
        "Replace with other healthy choices",
        Icons.Default.CoffeeMaker,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "No alcohol",
        "Life is way better without alcohol",
        Icons.Default.LocalBar,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        "No fried food",
        "An easy way to lose fat",
        Icons.Default.Fastfood,
        HabitColor.Amber.light
    ),
    DefaultHabitDetailItem(
        "Intermittent fasting",
        "Your stomach needs a little rest",
        Icons.Default.Timer,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        "Take greens & fruits",
        "Don't forget the natural snacks",
        Icons.Default.Eco,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "Have breakfast",
        "Wake up your body with energy",
        Icons.Default.BreakfastDining,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        "Drink 8 cups of water",
        "Keep your body hydrated",
        Icons.Default.LocalDrink,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        "Snack wisely",
        "Choose healthy snacks like nuts or fruits",
        Icons.Default.Spa,
        HabitColor.Lime.light
    ),
    DefaultHabitDetailItem(
        "Plan your meals",
        "Avoid overeating by staying organized",
        Icons.Default.CalendarToday,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        "Eat slowly",
        "Let your body recognize when it's full",
        Icons.Default.HourglassBottom,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        "Prepare home-cooked meals",
        "Control ingredients and portions",
        Icons.Default.Kitchen,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        "Avoid processed food",
        "Stick to whole and fresh ingredients",
        Icons.Default.Warning,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        "Monitor your portion size",
        "Keep your servings balanced",
        Icons.Default.Scale,
        HabitColor.Sand.light
    )
)

val easeStress = listOf(
    DefaultHabitDetailItem(
        "Practice meditation",
        "Connect with your inner peace",
        Icons.Default.SelfImprovement,
        HabitColor.SkyBlue.light
    ),
    DefaultHabitDetailItem(
        "Practice breathing",
        "Calm yourself from every in and out",
        Icons.Default.Air,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "Read a book",
        "Let your mind wander through stories",
        Icons.Default.MenuBook,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        "Keep a journal",
        "Know yourself better through writing",
        Icons.Default.Create,
        HabitColor.Rose.light
    ),
    DefaultHabitDetailItem(
        "Do stretching exercises",
        "Release tension and stress",
        Icons.Default.FitnessCenter,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "Do yoga",
        "Calming and smoothing",
        Icons.Default.SelfImprovement,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        "Get enough sleep",
        "Allow your body to recover and recharge",
        Icons.Default.Bedtime,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        "Enjoy nature",
        "Embrace nature to calm your mind",
        Icons.Default.NaturePeople,
        HabitColor.Lime.light
    ),
    DefaultHabitDetailItem(
        "Disconnect from devices",
        "Take a break from screens and notifications",
        Icons.Default.PhoneDisabled,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        "Laugh more",
        "Find a moment that makes you smile",
        Icons.Default.EmojiEmotions,
        HabitColor.Golden.light
    ),
    DefaultHabitDetailItem(
        "Allow yourself to cry",
        "Release emotional tension and feel relief",
        Icons.Default.Cloud,
        HabitColor.SkyBlue.light
    ),
    DefaultHabitDetailItem(
        "Declutter your space",
        "Organize your environment for mental clarity",
        Icons.Default.CleaningServices,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        "Practice gratitude",
        "Focus on positive aspects of your life",
        Icons.Default.Favorite,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        "Take a warm bath",
        "Feel the healing power of relaxation",
        Icons.Default.Bathtub,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        "Listen to calming music",
        "Let soothing melodies relax you",
        Icons.Default.MusicNote,
        HabitColor.Purple.light
    )
)

val gainSelfDiscipline = listOf(
    DefaultHabitDetailItem(
        "Limit screen time",
        "Reduce distractions by controlling screen use",
        Icons.Default.ScreenLockLandscape,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        "No emotional spending",
        "Distinguish your needs and wants",
        Icons.Default.AttachMoney,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "Go sugar-free",
        "See your body change with being sugar-free",
        Icons.Default.NoFood,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        "Avoid alcohol",
        "Life is way better without alcohol",
        Icons.Default.LocalBar,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        "Limit caffeine intake",
        "Replace with other healthy choices",
        Icons.Default.Coffee,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        "Quit smoking",
        "Don't have 'just one'",
        Icons.Default.SmokingRooms,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        "Avoid fried food",
        "An easy way to lose fat",
        Icons.Default.Fastfood,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        "No excessive gaming",
        "Winners don't just exist in games",
        Icons.Default.VideogameAsset,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "Wake up early",
        "Start your day with a productive mindset",
        Icons.Default.WbSunny,
        HabitColor.Golden.light
    ),
    DefaultHabitDetailItem(
        "Create a to-do list",
        "Plan your day to avoid procrastination",
        Icons.Default.List,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        "Set small goals",
        "Achieve success step by step",
        Icons.Default.Lightbulb,
        HabitColor.Amber.light
    ),
    DefaultHabitDetailItem(
        "Keep a daily journal",
        "Track your progress and self-growth",
        Icons.Default.Edit,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "Clean up your workspace",
        "Less mess equals less stress",
        Icons.Default.CleaningServices,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        "Practice delayed gratification",
        "Train yourself to resist instant pleasures",
        Icons.Default.HourglassEmpty,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        "Stay hydrated",
        "Drink water to maintain focus and energy",
        Icons.Default.LocalDrink,
        HabitColor.SkyBlue.light
    )
)

val leisureMoments = listOf(
    DefaultHabitDetailItem(
        "Watch a movie",
        "Get yourself a film therapy",
        Icons.Default.Movie,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        "Practice a hobby",
        "Fill your time, life won't pass you by",
        Icons.Default.Brush,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "Learn a new language",
        "Deepen your connection to other cultures",
        Icons.Default.Language,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        "Learn a musical instrument",
        "Run your fingers through your soul",
        Icons.Default.MusicNote,
        HabitColor.Rose.light
    ),
    DefaultHabitDetailItem(
        "Board games with friends",
        "More than just fun and games",
        Icons.Default.VideogameAsset,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "Draw or paint something",
        "Find your inner Van Gogh",
        Icons.Default.Palette,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        "Take a photo",
        "Record it as lifetime memories",
        Icons.Default.PhotoCamera,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        "Time for self-care",
        "Take time to nourish your body properly",
        Icons.Default.Spa,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        "Read a book",
        "Lose yourself in another world",
        Icons.Default.Book,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        "Go for a nature walk",
        "Reconnect with nature and yourself",
        Icons.Default.Forest,
        HabitColor.Lime.light
    ),
    DefaultHabitDetailItem(
        "Attend a live event",
        "Enjoy live music, theater, or sports",
        Icons.Default.Event,
        HabitColor.Rose.light
    ),
    DefaultHabitDetailItem(
        "Journal your thoughts",
        "Reflect on your experiences and emotions",
        Icons.Default.Edit,
        HabitColor.Golden.light
    ),
    DefaultHabitDetailItem(
        "Try a new recipe",
        "Experiment with flavors and cuisines",
        Icons.Default.Restaurant,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        "Stargaze",
        "Lose track of time under the night sky",
        Icons.Default.Star,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        "Call a friend",
        "Strengthen your connections",
        Icons.Default.Call,
        HabitColor.Teal.light
    )
)
val goodMorningHabits = listOf(
    DefaultHabitDetailItem(
        "Get up early",
        "Shine before the sun does",
        Icons.Default.WbSunny,
        HabitColor.Golden.light
    ),
    DefaultHabitDetailItem(
        "Dress up",
        "A good look lights up your day",
        Icons.Default.Checkroom,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        "Have breakfast",
        "Wake up your body with energy",
        Icons.Default.Restaurant,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        "Make the bed",
        "Clean your bed, clear your mind",
        Icons.Default.Bed,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        "Empty trash",
        "Leave home with no mess",
        Icons.Default.Delete,
        HabitColor.Rose.light
    ),
    DefaultHabitDetailItem(
        "Say 'You're the best!'",
        "Start your day with positive affirmations",
        Icons.Default.EmojiEmotions,
        HabitColor.Golden.light
    ),
    DefaultHabitDetailItem(
        "Warm up",
        "A natural morning pick-me-up",
        Icons.Default.FitnessCenter,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "Plan your day",
        "Set your intentions and priorities",
        Icons.Default.CalendarToday,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        "Meditate",
        "Connect with your inner peace",
        Icons.Default.SelfImprovement,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        "Hydrate",
        "Start your day with a glass of water",
        Icons.Default.LocalDrink,
        HabitColor.SkyBlue.light
    ),
    DefaultHabitDetailItem(
        "Listen to music",
        "Boost your mood with your favorite tunes",
        Icons.Default.MusicNote,
        HabitColor.Rose.light
    ),
    DefaultHabitDetailItem(
        "Stretch",
        "Release tension and wake up your muscles",
        Icons.Default.AirlineSeatReclineNormal,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "Smile at yourself in the mirror",
        "Acknowledge yourself with kindness",
        Icons.Default.Face,
        HabitColor.Orange.light
    )
)

val beforeSleepRoutineHabits = listOf(
    DefaultHabitDetailItem(
        "Stop eating before bed",
        "Your stomach also needs a good sleep",
        Icons.Default.NoFood,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        "Go to bed early",
        "Early to bed, early to rise",
        Icons.Default.Bedtime,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        "Stretch",
        "Release tension & stress",
        Icons.Default.FitnessCenter,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "Practice meditation",
        "Connect with your inner peace",
        Icons.Default.SelfImprovement,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        "Practice breathing",
        "Calm yourself from every in and out",
        Icons.Default.Air,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        "Keep a journal",
        "Reflect on your day and release thoughts",
        Icons.Default.EditNote,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        "Read a book",
        "Never enough books before bedtime",
        Icons.Default.MenuBook,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        "Take a bath",
        "Feel the healing power of a warm bath",
        Icons.Default.Bathtub,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        "Drink hot milk",
        "Relax your mind and body with warm milk",
        Icons.Default.LocalCafe,
        HabitColor.Amber.light
    ),
    DefaultHabitDetailItem(
        "Set tomorrow's plan",
        "Organize your tasks for a smooth day ahead",
        Icons.Default.EventNote,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        "Dim the lights",
        "Create a calming atmosphere",
        Icons.Default.LightMode,
        HabitColor.Golden.light
    ),
    DefaultHabitDetailItem(
        "Turn off screens",
        "Disconnect and let your mind unwind",
        Icons.Default.DoNotDisturb,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        "Express gratitude",
        "End your day with positive reflections",
        Icons.Default.ThumbUp,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "Visualize dreams",
        "Picture your ideal future before sleeping",
        Icons.Default.Paragliding,
        HabitColor.Purple.light
    )
)

val masterProductivityHabits = listOf(
    DefaultHabitDetailItem(
        "Set small goals",
        "Make goals more specific and achievable",
        Icons.Default.TipsAndUpdates,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        "Work with white noise",
        "A scientific way to overcome distraction",
        Icons.Default.Audiotrack,
        HabitColor.SkyBlue.light
    ),
    DefaultHabitDetailItem(
        "Prepare tomorrow's to-do list",
        "Make your time visible and countable",
        Icons.Default.List,
        HabitColor.Lime.light
    ),
    DefaultHabitDetailItem(
        "Get things done",
        "Never put off until tomorrow",
        Icons.Default.Task,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        "Clean up workspace",
        "Less mess equals less stress",
        Icons.Default.CleaningServices,
        HabitColor.Lime.light
    ),
    DefaultHabitDetailItem(
        "Block screen time",
        "Lock screen and glue attention",
        Icons.Default.PhoneLocked,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        "Get up early",
        "Shine before the sun does",
        Icons.Default.WbSunny,
        HabitColor.Amber.light
    ),
    DefaultHabitDetailItem(
        "Check all unread",
        "Zero your inbox",
        Icons.Default.Mail,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        "Focus on one task",
        "Avoid multitasking to increase efficiency",
        Icons.Default.CenterFocusStrong,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        "Track your progress",
        "Measure your achievements daily",
        Icons.Default.TrendingUp,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "Take regular breaks",
        "Stay productive without burnout",
        Icons.Default.AirlineSeatReclineNormal,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        "Set priorities",
        "Handle the most important tasks first",
        Icons.Default.PriorityHigh,
        HabitColor.Rose.light
    ),
    DefaultHabitDetailItem(
        "Review your goals",
        "Adjust your plans for continuous improvement",
        Icons.Default.AssignmentTurnedIn,
        HabitColor.Teal.light
    )
)

val strongerMindHabits = listOf(
    DefaultHabitDetailItem(
        "Read every day",
        "Expand your knowledge and creativity",
        Icons.Default.MenuBook,
        HabitColor.LeafGreen.light
    ),
    DefaultHabitDetailItem(
        "Meditate regularly",
        "Improve focus and reduce anxiety",
        Icons.Default.SelfImprovement,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        "Learn something new",
        "Expand your horizons daily",
        Icons.Default.School,
        HabitColor.Amber.light
    ),
    DefaultHabitDetailItem(
        "Keep a journal",
        "Reflect on your thoughts and experiences",
        Icons.Default.Edit,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        "Practice gratitude",
        "Appreciate the good things in your life",
        Icons.Default.ThumbUp,
        HabitColor.Orange.light
    ),
    DefaultHabitDetailItem(
        "Solve puzzles",
        "Keep your brain sharp and active",
        Icons.Default.Extension,
        HabitColor.DeepBlue.light
    ),
    DefaultHabitDetailItem(
        "Avoid negative thoughts",
        "Train your mind to focus on positivity",
        Icons.Default.RemoveCircle,
        HabitColor.BrickRed.light
    ),
    DefaultHabitDetailItem(
        "Practice deep breathing",
        "Calm your mind and body",
        Icons.Default.Air,
        HabitColor.SkyBlue.light
    ),
    DefaultHabitDetailItem(
        "Develop critical thinking",
        "Question and analyze everything critically",
        Icons.Default.Analytics,
        HabitColor.Teal.light
    ),
    DefaultHabitDetailItem(
        "Limit social media",
        "Protect your mind from digital overload",
        Icons.Default.SocialDistance,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        "Listen to podcasts",
        "Gain new perspectives and insights",
        Icons.Default.Podcasts,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        "Take mindful breaks",
        "Recharge by stepping away from work",
        Icons.Default.HourglassEmpty,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        "Learn a new language",
        "Enhance your cognitive abilities",
        Icons.Default.Language,
        HabitColor.Purple.light
    ),
    DefaultHabitDetailItem(
        "Maintain a balanced diet",
        "Fuel your brain with healthy nutrients",
        Icons.Default.NoMeals,
        HabitColor.Sand.light
    ),
    DefaultHabitDetailItem(
        "Develop problem-solving skills",
        "Challenge yourself to find solutions",
        Icons.Default.Lightbulb,
        HabitColor.Golden.light
    )
)